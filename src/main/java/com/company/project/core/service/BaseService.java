package com.company.project.core.service;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

import com.company.project.core.persistence.DynamicSpecifications;
import com.company.project.core.persistence.SearchFilter;
import com.company.project.core.model.BaseEntity;

import jakarta.persistence.*;
import org.apache.commons.collections4.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.transaction.annotation.Transactional;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;

public abstract class BaseService<T extends BaseEntity<ID>, ID extends Serializable> {

    @PersistenceContext
    private EntityManager entityManager;

    protected final JpaRepositoryImplementation<T, ID> repository;

    private final Class<T> modelClass;
    private final ModelMapper modelMapper;

    protected BaseService(JpaRepositoryImplementation<T, ID> repository, Class<T> modelClass, ModelMapper modelMapper) {
        this.repository = repository;
//        this.modelClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        this.modelClass = modelClass;
        this.modelMapper = modelMapper;
    }

    public <X, Y> Y map(X source, Class<Y> targetClass) {
        return modelMapper.map(source, targetClass);
    }

    // 更新映射方法
    public <X, Y> void update(X source, Y target) {
        modelMapper.map(source, target);
    }

    /**
     * 首字母大写
     */
    private String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * 使用setter方法更新（更安全的方式）
     */
    public void updateWithSetter(T entity, String fieldName, Object value) {
        if (value != null && entity != null) {
            try {
                String setterName = "set" + capitalize(fieldName);
                Method setter = findMethod(entity.getClass(), setterName, value.getClass());
                setter.invoke(entity, value);
            } catch (Exception e) {
                throw new RuntimeException("调用setter方法失败: " + fieldName, e);
            }
        }
    }

    /**
     * 递归查找字段（包括父类字段）
     */
    private Field getField(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            Class<?> superClass = clazz.getSuperclass();
            if (superClass != null && superClass != Object.class) {
                return getField(superClass, fieldName);
            }
            throw e;
        }
    }

    /**
     * 更新所有非空字段
     */
    private void updateFields(T target, T source) {
        try {
            Field[] fields = modelClass.getDeclaredFields();

            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(source);

                if (value != null) {
                    Field targetField = getField(modelClass, field.getName());
                    targetField.setAccessible(true);
                    targetField.set(target, value);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("更新字段失败", e);
        }
    }

    private void updateFields2Spring(Object target, Object source) {
//        BeanWrapper sourceWrapper = new BeanWrapperImpl(source);
//        BeanWrapper targetWrapper = new BeanWrapperImpl(target);
//
//        PropertyDescriptor[] propertyDescriptors = sourceWrapper.getPropertyDescriptors();
//
//        for (PropertyDescriptor pd : propertyDescriptors) {
//            String propertyName = pd.getName();
//            if ("id".equals(propertyName)) {
//                continue;
//            }
//            // 检查属性是否可读
//            if (sourceWrapper.isReadableProperty(propertyName)) {
//                Object value = sourceWrapper.getPropertyValue(propertyName);
//                if (value != null) {
//                    if (targetWrapper.isWritableProperty(propertyName)) {
//                        targetWrapper.setPropertyValue(propertyName, value);
//                    }
//                }
//            }
//        }

        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }
    /**
     * 获取源对象中值为null的属性名数组
     */
    private String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            String propName = pd.getName();
            if ("id".equals(propName)) {
                continue;
            }
            Object srcValue = src.getPropertyValue(propName);
            if (srcValue == null) {
                emptyNames.add(propName);
            }
        }
        return emptyNames.toArray(new String[0]);
    }


    /**
     * 查找方法
     */
    private Method findMethod(Class<?> clazz, String methodName, Class<?> paramType) {
        try {
            return clazz.getMethod(methodName, paramType);
        } catch (NoSuchMethodException e) {
            // 尝试查找父类方法
            Class<?> superClass = clazz.getSuperclass();
            if (superClass != null && superClass != Object.class) {
                return findMethod(superClass, methodName, paramType);
            }
            throw new RuntimeException("找不到方法: " + methodName, e);
        }
    }

    /**
     * 使用原生SQL的部分更新（性能更好）
     */
    public void partialUpdateNative(Long id, Class<T> entityClass, Map<String, Object> updates) {
        StringBuilder sql = new StringBuilder("UPDATE ");
        // 获取表名（这里需要根据注解或约定获取）
        String tableName = getTableName(entityClass);
        sql.append(tableName).append(" SET ");

        // 构建SET子句
        boolean first = true;
        for (String fieldName : updates.keySet()) {
            if (!first) {
                sql.append(", ");
            }
            sql.append(fieldName).append(" = :").append(fieldName);
            first = false;
        }
        sql.append(" WHERE id = :id");

        // 执行更新
        Query query = entityManager.createNativeQuery(sql.toString());
        updates.forEach((key, value) -> query.setParameter(key, value));
        query.setParameter("id", id);
        query.executeUpdate();
    }

    /**
     * 获取表名
     */
    private String getTableName(Class<T> entityClass) {
        Table tableAnnotation = entityClass.getAnnotation(Table.class);
        if (tableAnnotation != null && !tableAnnotation.name().isEmpty()) {
            return tableAnnotation.name();
        }
        return entityClass.getSimpleName().toLowerCase();
    }

    public T save(T entity) {
        if (entity.getId() == null)
            return create(entity);
        else
            return updateEntityPartially(entity.getId(), entity);
    }

    @Timed(value = "service.update", description = "Update entity")
    @Counted(value = "service.update.calls", description = "Update calls")
    @Transactional
    public T updateEntityPartially(ID id, T updatedEntity) {
        T existingEntity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("实体不存在"));

//        updateFields2Spring(existingEntity, updatedEntity);
        modelMapper.map(updatedEntity, existingEntity);

        return repository.save(existingEntity);
    }


    @Timed(value = "service.create", description = "Create entity")
    @Counted(value = "service.create.calls", description = "Create calls")
    @Transactional
    public T create(T entity) {
        return repository.save(entity);
    }
    @Timed(value = "service.update", description = "Update entity")
    @Counted(value = "service.update.calls", description = "Update calls")
    @Transactional
    public T update(T entity) {
        return repository.save(entity);
    }

    @Timed(value = "service.find.byId", description = "Find entity by ID")
    @Counted(value = "service.find.byId.calls", description = "Find by ID calls")
    public Optional<T> findById(ID id) {
        return repository.findById(id);
    }

    @Timed(value = "service.find.all", description = "Find all entities")
    @Counted(value = "service.find.all.calls", description = "Find all calls")
    public List<T> findAll() {
        return repository.findAll();
    }

    @Timed(value = "service.find.page", description = "Find entities with pagination")
    @Counted(value = "service.find.page.calls", description = "Page find calls")
    public Page<T> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Timed(value = "service.create.all", description = "Create multiple entities")
    @Counted(value = "service.create.all.calls", description = "Batch Create calls")
    @Transactional
    public List<T> createAll(List<T> entities) {
        return repository.saveAll(entities);
    }

    @Timed(value = "service.delete", description = "Delete entity")
    @Counted(value = "service.delete.calls", description = "Delete calls")
    @Transactional
    public void delete(T entity) {
        repository.delete(entity);
    }

    @Timed(value = "service.delete.byId", description = "Delete entity by ID")
    @Counted(value = "service.delete.byId.calls", description = "Delete by ID calls")
    @Transactional
    public boolean deleteById(ID id) {
        T existingEntity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("实体不存在"));
         repository.delete(existingEntity);
         return true;
    }
//
//    @Timed(value = "service.delete.byIds", description = "Delete entity by IDs")
//    @Counted(value = "service.delete.byIds.calls", description = "Delete by IDs calls")
//    @Transactional
//    public int deleteByIds(List<ID> ids) {
//        if (CollectionUtils.isEmpty(ids)) {
//            return 0;
//        }
//        return repository.deleteByIdsWithCount(ids);
//    }

//    public boolean safeDeleteUser(ID id) {
//        T entity = repository.findById(id).orElse(null);
//        if (entity != null && canDelete(id)) {
//            repository.deleteById(id);
//            return true;
//        }
//        return false;
//    }

    @Timed(value = "service.delete.all", description = "Delete all entities")
    @Counted(value = "service.delete.all.calls", description = "Delete all calls")
    @Transactional
    public void deleteAll() {
        repository.deleteAll();
    }

    @Timed(value = "service.count", description = "Count entities")
    @Counted(value = "service.count.calls", description = "Count calls")
    public long count() {
        return repository.count();
    }

    @Timed(value = "service.exists", description = "Check if entity exists")
    @Counted(value = "service.exists.calls", description = "Exists checks")
    public boolean existsById(ID id) {
        return repository.existsById(id);
    }

    public Page<T> search(Map<String, Object> searchParams, Pageable pageable) {
        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
        Specification<T> spec = DynamicSpecifications.bySearchFilter(filters.values(), modelClass);
        return repository.findAll(spec, pageable);
    }
}

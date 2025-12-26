package com.company.project.core.service;

import java.io.Serializable;
import java.util.*;

import com.company.project.core.dto.BaseDTO;
import com.company.project.core.dto.BaseUpdateDTO;
import com.company.project.core.exception.ResourceNotFoundException;
import com.company.project.core.mapper.GenericMapper;
import com.company.project.core.persistence.DynamicSpecifications;
import com.company.project.core.persistence.SearchFilter;
import com.company.project.core.model.BaseEntity;

import com.company.project.core.vo.BaseVO;
import jakarta.persistence.*;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.transaction.annotation.Transactional;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;

@RequiredArgsConstructor
public abstract class BaseService<ENTITY extends BaseEntity<ID>, VO extends BaseVO<ID>, CREATE_DTO extends BaseDTO, UPDATE_DTO extends BaseUpdateDTO<ID>, ID extends Serializable> {

    @PersistenceContext
    private EntityManager entityManager;

    protected final JpaRepositoryImplementation<ENTITY, ID> repository;

    private final Class<ENTITY> modelClass;

    protected final GenericMapper<ENTITY, VO, CREATE_DTO, UPDATE_DTO, ID> mapper;

    /**
     * 使用原生SQL的部分更新（性能更好）
     */
    public void partialUpdateNative(Long id, Class<ENTITY> entityClass, Map<String, Object> updates) {
        StringBuilder sql = new StringBuilder("UPDATE ");
        String tableName = getTableName(entityClass);
        sql.append(tableName).append(" SET ");

        boolean first = true;
        for (String fieldName : updates.keySet()) {
            if (!first) {
                sql.append(", ");
            }
            sql.append(fieldName).append(" = :").append(fieldName);
            first = false;
        }
        sql.append(" WHERE id = :id");

        Query query = entityManager.createNativeQuery(sql.toString());
        updates.forEach((key, value) -> query.setParameter(key, value));
        query.setParameter("id", id);
        query.executeUpdate();
    }

    private String getTableName(Class<ENTITY> entityClass) {
        Table tableAnnotation = entityClass.getAnnotation(Table.class);
        if (tableAnnotation != null && !tableAnnotation.name().isEmpty()) {
            return tableAnnotation.name();
        }
        return entityClass.getSimpleName().toLowerCase();
    }

    @Timed(value = "service.create", description = "Create entity")
    @Counted(value = "service.create.calls", description = "Create calls")
    @Transactional
    public VO create(CREATE_DTO dto) {
        ENTITY entity = mapper.toEntity(dto);
        ENTITY savedEntity = repository.save(entity);
        return mapper.toVO(savedEntity);
    }
    @Timed(value = "service.update", description = "Update entity")
    @Counted(value = "service.update.calls", description = "Update calls")
    @Transactional
    public VO update(UPDATE_DTO dto) {
        ENTITY entity = repository.findById(getIdFromUpdateDto(dto))
                .orElseThrow(() -> new ResourceNotFoundException("Entity not found", modelClass.getSimpleName())); //TODO

        mapper.updateEntityFromDto(dto, entity);
        ENTITY updatedEntity = repository.save(entity);
        return mapper.toVO(updatedEntity);
    }

    @Timed(value = "service.find.byId", description = "Find entity by ID")
    @Counted(value = "service.find.byId.calls", description = "Find by ID calls")
    public Optional<VO> findById(ID id) {
        return repository.findById(id).map(mapper::toVO);
    }

    @Timed(value = "service.find.byId", description = "Find entity by ID")
    @Counted(value = "service.find.byId.calls", description = "Find by ID calls")
    public Optional<UPDATE_DTO> findByIdForEdit(ID id) {
        return repository.findById(id).map(mapper::toDTO);
    }

    @Timed(value = "service.find.all", description = "Find all entities")
    @Counted(value = "service.find.all.calls", description = "Find all calls")
    public List<VO> findAll() {
        return mapper.toVOList(repository.findAll());
    }

    @Timed(value = "service.find.page", description = "Find entities with pagination")
    @Counted(value = "service.find.page.calls", description = "Page find calls")
    public Page<VO> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toVO);
    }

    @Timed(value = "service.create.all", description = "Create multiple entities")
    @Counted(value = "service.create.all.calls", description = "Batch Create calls")
    @Transactional
    public List<ENTITY> createAll(List<ENTITY> entities) {
        return repository.saveAll(entities);
    }

    @Timed(value = "service.delete", description = "Delete entity")
    @Counted(value = "service.delete.calls", description = "Delete calls")
    @Transactional
    public void delete(ENTITY entity) {
        repository.delete(entity);
    }

    @Timed(value = "service.delete.byId", description = "Delete entity by ID")
    @Counted(value = "service.delete.byId.calls", description = "Delete by ID calls")
    @Transactional
    public boolean deleteById(ID id) {
        ENTITY existingEntity = repository.findById(id)
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

    public Page<ENTITY> search(Map<String, Object> searchParams, Pageable pageable) {
        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
        Specification<ENTITY> spec = DynamicSpecifications.bySearchFilter(filters.values(), modelClass);
        return repository.findAll(spec, pageable);
    }

    protected abstract ID getIdFromUpdateDto(UPDATE_DTO updateDto);
}

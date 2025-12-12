package com.company.project.core.service;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.company.project.core.dao.BaseRepository;
import com.company.project.core.dao.persistence.DynamicSpecifications;
import com.company.project.core.dao.persistence.SearchFilter;
import com.company.project.core.model.BaseEntity;
import com.company.project.domain.School;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.core.ResolvableType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public abstract class BaseService<T extends BaseEntity, ID extends Serializable> {

    @PersistenceContext
    private EntityManager entityManager;

    protected BaseRepository<T, ID> repository;

    private Class<T> modelClass;

    protected BaseService(BaseRepository<T, ID> repository) {
        this.repository = repository;
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        modelClass = (Class<T>) pt.getActualTypeArguments()[0];
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
        return repository.deleteByIdWithCount(id) > 0;
    }

    @Timed(value = "service.delete.byIds", description = "Delete entity by IDs")
    @Counted(value = "service.delete.byIds.calls", description = "Delete by IDs calls")
    @Transactional
    public int deleteByIds(List<ID> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return 0;
        }
        return repository.deleteByIdsWithCount(ids);
    }

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

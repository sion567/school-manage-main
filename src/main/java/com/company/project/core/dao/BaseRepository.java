package com.company.project.core.dao;

import java.io.Serializable;
import java.util.List;

import com.company.project.core.model.BaseEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity, ID extends Serializable>
        extends JpaSpecificationExecutor<T>, JpaRepository<T, ID> {

    @Modifying
    @Query("DELETE FROM #{#entityName} e WHERE e.id = :id")
    int deleteByIdWithCount(@Param("id") ID id);

    @Modifying
    @Query("DELETE FROM #{#entityName} e WHERE e.id IN :ids")
    int deleteByIdsWithCount(@Param("ids") List<ID> ids);
}

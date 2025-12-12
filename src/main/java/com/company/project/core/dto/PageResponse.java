package com.company.project.core.dto;

import java.util.List;

public record PageResponse<T> (
    List<T> content,
    Long totalElements,
    Integer totalPages,
    Integer currentPage,
    Integer pageSize,
    Boolean hasNext,
    Boolean hasPrevious
) {

}

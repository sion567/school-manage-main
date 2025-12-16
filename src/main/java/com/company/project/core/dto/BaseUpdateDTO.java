package com.company.project.core.dto;

import java.io.Serializable;

public interface BaseUpdateDTO<ID extends Serializable> extends BaseDTO {
    ID getId();
}

package com.company.project.core.web;

import java.io.Serializable;
import java.util.List;

import com.company.project.core.dto.BaseDTO;
import com.company.project.core.dto.BaseUpdateDTO;
import com.company.project.core.exception.BusinessException;
import com.company.project.core.model.BaseEntity;
import com.company.project.core.service.BaseService;

import com.company.project.core.vo.BaseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "通用 CRUD 操作", description = "通用的增删改查操作接口")
@RequiredArgsConstructor
public abstract class CrudRestController<T extends BaseEntity<ID>, VO extends BaseVO<ID>, CREATE_DTO extends BaseDTO, UPDATE_DTO extends BaseUpdateDTO<ID>, ID extends Serializable> extends BaseRestController {

    protected final BaseService<T, VO, CREATE_DTO, UPDATE_DTO, ID> crudService;

    protected abstract Class<T> getEntityType();

    @Operation(summary = "创建实体", description = "创建一个新的实体")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "创建成功",
                    content = @Content(schema = @Schema(implementation = ResponseResult.class))),
            @ApiResponse(responseCode = "400", description = "请求参数错误"),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @PostMapping
    public ResponseEntity<ResponseResult<VO>> create(
            @Parameter(description = "实体信息", required = true)
            @Valid @RequestBody CREATE_DTO dto) {
        try {
            validateEntity(dto);
            VO createdEntity = crudService.create(dto);
            return success("创建成功", createdEntity);
        } catch (Exception e) {
            return error("创建失败: " + e.getMessage());
        }
    }

    @Operation(summary = "获取实体详情", description = "根据ID获取实体详情")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "获取成功"),
            @ApiResponse(responseCode = "404", description = "实体不存在"),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ResponseResult<VO>> getById(@PathVariable ID id) {
        try {
            validateNotNull(id, "ID不能为空");
            return crudService.findById(id)
                .map(this::success)
                .orElse(error(404, "数据不存在"));
        } catch (Exception e) {
            return error("查询失败: " + e.getMessage());
        }
    }

    @Operation(summary = "更新实体", description = "根据ID更新实体信息")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "更新成功"),
            @ApiResponse(responseCode = "404", description = "实体不存在"),
            @ApiResponse(responseCode = "400", description = "请求参数错误"),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ResponseResult<VO>> update(@PathVariable ID id, @RequestBody UPDATE_DTO dto) {
        try {
            validateNotNull(id, "ID不能为空");
            validateEntity(dto);

            if (!id.equals(dto.getId())) {
                return error("ID不匹配");
            }

            VO result = crudService.update(dto);
            return success("更新成功", result);
        } catch (Exception e) {
            return error("更新失败: " + e.getMessage());
        }
    }

    @Operation(summary = "删除实体", description = "根据ID删除实体")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "删除成功"),
            @ApiResponse(responseCode = "404", description = "实体不存在"),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseResult<String>> delete(@PathVariable ID id) {
        try {
            validateNotNull(id, "ID不能为空");
            boolean deleted = crudService.deleteById(id);
            if (deleted) {
                return success("删除成功");
            } else {
                return error(404, "数据不存在");
            }
        } catch (Exception e) {
            return error("删除失败: " + e.getMessage());
        }
    }

    @Operation(summary = "分页查询实体", description = "分页查询实体列表")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "查询成功"),
            @ApiResponse(responseCode = "400", description = "请求参数错误"),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @GetMapping
    public ResponseEntity<ResponseResult<PageResponse<VO>>> findAll(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        try {
            Page<VO> page = crudService.findAll(pageable);
            return pageResponse(new PageRequest(pageable.getPageNumber() + 1, pageable.getPageSize()),
                    new PageResult<>(page.getTotalElements(), page.getContent()));
        } catch (Exception e) {
            return error("查询失败: " + e.getMessage());
        }
    }

    /**
     * 批量删除
     */
//    @DeleteMapping("/batch")
//    public ResponseEntity<ResponseResult<String>> batchDelete(@RequestBody List<ID> ids) {
//        try {
//            validateNotNull(ids, "ID列表不能为空");
//            if (ids.isEmpty()) {
//                return error("ID列表不能为空");
//            }
//            int deletedCount = getService().deleteByIds(ids);
//            return success("批量删除成功，共删除" + deletedCount + "条记录");
//        } catch (Exception e) {
//            return error("批量删除失败: " + e.getMessage());
//        }
//    }


    protected void validateEntity(BaseDTO dto) {
        if (dto == null) {
            throw new BusinessException("实体不能为空");
        }
        // 可以在这里添加具体的验证逻辑
    }

    /**
     * 批量验证
     */
    protected void validateEntities(List<BaseDTO> list) {
        if (list == null) {
            throw new BusinessException("实体列表不能为空");
        }
        for (BaseDTO dto : list) {
            validateEntity(dto);
        }
    }

    /**
     * 获取实体名称（用于日志和错误信息）
     */
    protected String getEntityName() {
        return getEntityType().getSimpleName();
    }

    /**
     * 通用的分页查询方法
     */
    protected ResponseEntity<ResponseResult<PageResponse<T>>> handlePageQuery(
            Pageable pageable,
            java.util.function.Function<Pageable, Page<T>> queryFunction) {
        try {
            Page<T> page = queryFunction.apply(pageable);
            return pageResponse(new PageRequest(pageable.getPageNumber() + 1, pageable.getPageSize()),
                    new PageResult<>(page.getTotalElements(), page.getContent()));
        } catch (Exception e) {
            return error("查询失败: " + e.getMessage());
        }
    }
}
package com.company.project.core.web;

import com.company.project.core.dto.BaseDTO;
import com.company.project.core.dto.BaseUpdateDTO;
import com.company.project.core.exception.BusinessException;
import com.company.project.core.exception.ResourceNotFoundException;
import com.company.project.core.model.BaseEntity;
import com.company.project.core.service.BaseService;
import com.company.project.core.vo.BaseVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.Serializable;
import java.net.http.HttpRequest;

@Controller
@RequiredArgsConstructor
public abstract class CrudController<T extends BaseEntity<ID>, VO extends BaseVO<ID>, CREATE_DTO extends BaseDTO, UPDATE_DTO extends BaseUpdateDTO<ID>, ID extends Serializable> extends BaseController {

    protected final BaseService<T, VO, CREATE_DTO, UPDATE_DTO, ID> crudService;
    protected final String basePath;
    protected final String listView;
    protected final String inputView;
    protected final String editView;

    @ModelAttribute("basePath")
    public String getBasePath() {
        return basePath;
    }

    @GetMapping
    public String list(HttpServletRequest req, Model model) {
        return listWithSpecialLogic(req, model);
    }

    protected String listWithSpecialLogic(HttpServletRequest request,
                                          Model model) {
        model.addAttribute("entities", crudService.findAll());
        return listView;
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("dto", createNewDto());
        return inputView;
    }

    @PostMapping
    public String create(@Valid @ModelAttribute CREATE_DTO dto,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            return handleSaveError(model, bindingResult, inputView);
        }
        crudService.create(dto);
        return handleSaveSuccess(model, getCreateSuccessMessage(), basePath);
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable ID id, Model model) {
        model.addAttribute("dto", crudService.findByIdForEdit(id).orElseThrow(() ->
                new ResourceNotFoundException("Entity not found with id: " + id, null)));
        model.addAttribute("updatePath", basePath + "/" + id); // @{/ui/school/{id}(id=${dto.id})}
        return editView;
    }

    @PostMapping("/{id}")
    public String update(@PathVariable ID id,
                         @Valid @ModelAttribute UPDATE_DTO dto,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            return handleSaveError(model, bindingResult, editView);
        }
        crudService.update(dto);
        return handleSaveSuccess(model, getUpdateSuccessMessage(), basePath);
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable ID id) {
        try {
            crudService.deleteById(id);
            return handleDeleteSuccess(basePath);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("资源不存在", "resource id:" + id);
        } catch (Exception e) {
            throw new BusinessException("删除数据失败", e);
        }
    }

    // 子类必须实现的抽象方法
    protected abstract CREATE_DTO createNewDto();
    protected String getCreateSuccessMessage() {
        return "信息创建成功！";
    }
    protected String getUpdateSuccessMessage() {
        return "信息变更成功！";
    }
}

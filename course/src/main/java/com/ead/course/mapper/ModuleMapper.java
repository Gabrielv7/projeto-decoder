package com.ead.course.mapper;

import com.ead.course.domain.Module;
import com.ead.course.domain.dto.request.ModuleRequest;
import com.ead.course.domain.dto.response.ModuleResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ModuleMapper {

    private final ModelMapper mapper;

    public Module toEntity(ModuleRequest moduleRequest){
        return mapper.map(moduleRequest, Module.class);
    }

    public ModuleResponse toResponse(Module module){
        return mapper.map(module, ModuleResponse.class);
    }

    public Page<ModuleResponse> convertToPageModulesResponse(Page<Module> modules) {
        return modules.map(this::toResponse);
    }

}

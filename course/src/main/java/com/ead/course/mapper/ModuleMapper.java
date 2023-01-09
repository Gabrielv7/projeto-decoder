package com.ead.course.mapper;

import com.ead.course.domain.Module;
import com.ead.course.domain.dto.request.ModuleRequest;
import com.ead.course.domain.dto.response.ModuleResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ModuleMapper {

    @Autowired
    private ModelMapper mapper;

    public Module toEntity(ModuleRequest moduleRequest){
        return mapper.map(moduleRequest, Module.class);
    }

    public ModuleResponse toResponse(Module module){
        return mapper.map(module, ModuleResponse.class);
    }

    public List<ModuleResponse> toCollectionResponse(List<Module> modules){
        return modules.stream().map(this::toResponse).collect(Collectors.toList());
    }

}

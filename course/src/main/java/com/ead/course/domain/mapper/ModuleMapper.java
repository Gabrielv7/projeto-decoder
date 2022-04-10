package com.ead.course.domain.mapper;

import com.ead.course.domain.models.dtos.ModuleDto;
import com.ead.course.domain.models.forms.ModuleForm;
import com.ead.course.domain.models.ModuleModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ModuleMapper {

    @Autowired
    ModelMapper modelMapper;

    public ModuleModel ToEntity(ModuleForm moduleForm){

        return modelMapper.map(moduleForm, ModuleModel.class);

    }

    public ModuleDto toDto (ModuleModel moduleModel){

        return modelMapper.map(moduleModel, ModuleDto.class);

    }
    
    public List<ModuleDto> toCollectionDto(List<ModuleModel> modules){

        return modules.stream().map(this::toDto).collect(Collectors.toList());
    }


}

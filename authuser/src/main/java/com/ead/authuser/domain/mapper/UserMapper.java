package com.ead.authuser.domain.mapper;

import com.ead.authuser.domain.model.dtos.UserDto;
import com.ead.authuser.domain.model.forms.UserForm;
import com.ead.authuser.domain.model.UserModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    @Autowired
    ModelMapper modelMapper;

    public UserDto toDto(UserModel userModel){

        return modelMapper.map(userModel, UserDto.class);

    }

    public UserModel toEntity(UserForm userForm){

        return modelMapper.map(userForm, UserModel.class);

    }

    public List<UserDto> toCollectionDto(List<UserModel> userModels){

       return userModels.stream().map(this::toDto).collect(Collectors.toList());

    }

}

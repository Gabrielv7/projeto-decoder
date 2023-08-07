package com.ead.authuser.service.impl;

import com.ead.authuser.model.Role;
import com.ead.authuser.model.enums.RoleTypeEnum;
import com.ead.authuser.exception.NotFoundException;
import com.ead.authuser.repository.RoleRepository;
import com.ead.authuser.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final MessageSource messageSource;

    @Override
    public Role findByRoleType(RoleTypeEnum roleType) {

        return roleRepository.findByRoleType(roleType).
                orElseThrow(() -> new NotFoundException(
                        messageSource.getMessage("role-not-found", null, LocaleContextHolder.getLocale()))
                );

    }

}

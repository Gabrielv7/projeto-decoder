package com.ead.authuser.service.impl;

import com.ead.authuser.repository.RoleRepository;
import com.ead.authuser.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;


}

package com.ead.authuser.service;

import com.ead.authuser.domain.model.Role;
import com.ead.authuser.domain.model.enums.RoleTypeEnum;

public interface RoleService {

   Role findByRoleType(RoleTypeEnum roleType);

}

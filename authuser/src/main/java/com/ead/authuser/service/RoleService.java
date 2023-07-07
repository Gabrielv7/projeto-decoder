package com.ead.authuser.service;

import com.ead.authuser.domain.Role;
import com.ead.authuser.domain.enums.RoleTypeEnum;

public interface RoleService {

   Role findByRoleType(RoleTypeEnum roleType);

}

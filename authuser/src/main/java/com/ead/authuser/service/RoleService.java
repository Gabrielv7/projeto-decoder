package com.ead.authuser.service;

import com.ead.authuser.model.Role;
import com.ead.authuser.model.enums.RoleTypeEnum;

public interface RoleService {

   Role findByRoleType(RoleTypeEnum roleType);

}

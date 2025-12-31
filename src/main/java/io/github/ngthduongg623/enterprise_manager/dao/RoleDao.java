package io.github.ngthduongg623.enterprise_manager.dao;

import io.github.ngthduongg623.enterprise_manager.entity.Role;

public interface RoleDao {

	public Role findRoleByName(String theRoleName);
}

package app.service;

import app.model.Role;

import java.util.HashSet;
import java.util.Set;

public interface RoleService {

    void add(Role role);
    void deleteRoleByName(String roleName);
    Role getByName(String name);
    Set<Role> getAllRoles();
    Set<Role> getRoleSingletonSetByName(String roleName);
}

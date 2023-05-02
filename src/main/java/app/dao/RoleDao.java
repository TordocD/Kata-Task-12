package app.dao;

import app.model.Role;
import java.util.Set;

public interface RoleDao {

    void add(Role role);
    void deleteRoleByName(String roleName);
    Role getByName(String name);
    Set<Role> getAllRoles();
}

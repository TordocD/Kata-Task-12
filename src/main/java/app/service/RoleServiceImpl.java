package app.service;

import app.dao.RoleDao;
import app.model.Role;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;


import java.util.Collections;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleDao roleDao;

    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    @Transactional
    public void add(Role role) {
        roleDao.add(role);
    }

    @Override
    @Transactional
    public void deleteRoleByName(String roleName) {
        roleDao.deleteRoleByName(roleName);
    }

    @Override
    @Transactional(readOnly = true)
    public Role getByName(String name) {
        return roleDao.getByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Role> getAllRoles() {
        return roleDao.getAllRoles();
    }

    @Override
    @Transactional
    public Set<Role> getRoleSingletonSetByName(String roleName) {
        return Collections.singleton(getByName(roleName));
    }


}

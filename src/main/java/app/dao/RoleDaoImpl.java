package app.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import app.model.Role;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

@Repository
public class RoleDaoImpl implements RoleDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void add(Role role) {
        entityManager.persist(role);
    }

    @Override
    public void deleteRoleByName(String roleName) {
        entityManager.createQuery("DELETE Role WHERE roleName = :roleName")
                .setParameter("roleName", roleName).executeUpdate();
    }

    @Override
    public Role getByName(String roleName) {
        try {
            return entityManager.createQuery("FROM Role WHERE roleName = :roleName", Role.class)
                    .setParameter("roleName", roleName).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Set<Role> getAllRoles() {
        return new HashSet<>(entityManager.createQuery("FROM Role", Role.class).getResultList());
    }


}

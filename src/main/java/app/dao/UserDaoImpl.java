package app.dao;

import app.model.User;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {


    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("FROM User", User.class).getResultList();
    }

    @Override
    public User getById(int id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void add(User user) {
        entityManager.persist(user);
    }

    @Override
    public void deleteById(Integer id) {
        entityManager.createQuery("DELETE User WHERE id= :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public void updateUser(User newUser) {
        entityManager.merge(newUser);
    }

    @Override
    public User getByUsername(String username) {
        try {
            return entityManager
                    .createQuery("SELECT user FROM User user LEFT JOIN FETCH user.roles WHERE user.username=:username",
                            User.class)
                    .setParameter("username", username).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}

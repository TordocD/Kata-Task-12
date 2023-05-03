package app.service;

import app.model.Role;
import app.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {

    List<User> getAllUsers();
    User getById(int id);
    void deleteById(Integer id);
    void updateUser(User newUser);
    User getByUsername(String username);
    boolean saveUser(User user);
}

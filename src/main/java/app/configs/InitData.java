package app.configs;

import app.model.Role;
import app.model.User;
import app.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import java.util.Set;

@Component
public class InitData {

    private final UserService userService;

    public InitData(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    public void init() {
        Set<Role> roles = Set.of(new Role("ADMIN"), new Role("USER"));

        if (userService.getByUsername("admin@mail.ru") == null) {
            userService.saveUser(new User("admin@mail.ru", "admin", roles));
        }
    }
}

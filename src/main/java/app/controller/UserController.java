package app.controller;

import app.model.Role;
import app.model.User;
import app.service.RoleService;
import app.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashSet;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    public final RoleService roleService;

    public UserController (UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String getAllUsers(ModelMap model, Principal principal) {
        model.addAttribute("currentUser", userService.getByUsername(principal.getName()));

        return "user";
    }

    @PatchMapping("/update")
    public String updateUser(@ModelAttribute User currentUser,
                             @RequestParam("chosenRoles") HashSet<String> chosenRoles) {
        HashSet<Role> roles = new HashSet<>();

        for (String chosenRole : chosenRoles) {
            Role role = roleService.getByName(chosenRole);
            roles.add(role);
        }
        currentUser.setRoles(roles);
        userService.updateUser(currentUser);

        return "redirect:/user";
    }
}

package app.controller;



import app.model.Role;
import app.model.User;
import app.service.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import app.service.UserService;
import java.util.HashSet;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminController (UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String getAllUsers(ModelMap model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("allRoles", roleService.getAllRoles());
        model.addAttribute("updatedUser", new User());
        model.addAttribute("deletedUser", new User());
        model.addAttribute("addedUser", new User());

        return "admin";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute User addedUser,
                          @RequestParam("chosenRoles") HashSet<String> chosenRoles) {
        HashSet<Role> roles = new HashSet<>();
        for(String chosenRole : chosenRoles) {
            Role role = roleService.getByName(chosenRole);
            roles.add(role);
        }
        addedUser.setRoles(roles);
        userService.saveUser(addedUser);

        return "redirect:/admin";
    }

    @DeleteMapping("/delete")
    public String deleteUser(@ModelAttribute User deletedUser) {
        if (!deletedUser.getUsername().equals("admin@mail.ru")) {
            userService.deleteById(deletedUser.getId());
        }

        return "redirect:/admin";
    }

    @PatchMapping("/update")
    public String updateUser(@ModelAttribute User updatedUser,
                             @RequestParam("chosenRoles") HashSet<String> chosenRoles) {
        HashSet<Role> roles = new HashSet<>();

        for (String chosenRole : chosenRoles) {
            Role role = roleService.getByName(chosenRole);
            roles.add(role);
            }
        updatedUser.setRoles(roles);
        userService.updateUser(updatedUser);

        return "redirect:/admin";
    }

    @PostMapping("/add_new_role")
    public String addNewRole(@RequestParam String newRoleName) {
        roleService.add(new Role(newRoleName));

        return "redirect:/admin";
    }

    @DeleteMapping("/delete_role")
    public String deleteRole(@RequestParam String deletedRoleName) {

            if (!deletedRoleName.equals("USER") && !deletedRoleName.equals("ADMIN")) {
                roleService.deleteRoleByName(deletedRoleName);
            }

        return "redirect:/admin";
    }
}

package bg.softuni.pethotel.init;

import bg.softuni.pethotel.service.CommentService;
import bg.softuni.pethotel.service.RoleService;
import bg.softuni.pethotel.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DBInit implements CommandLineRunner {

    private final UserService userService;
    private final RoleService roleService;
    private final CommentService commentService;

    public DBInit(UserService userService, RoleService roleService, CommentService commentService) {
        this.userService = userService;
        this.roleService = roleService;
        this.commentService = commentService;
    }

    @Override
    public void run(String... args) throws Exception {
        roleService.seedRoles();
        userService.seedInitialUsers();
//        commentService.seedTestComments();
    }
}

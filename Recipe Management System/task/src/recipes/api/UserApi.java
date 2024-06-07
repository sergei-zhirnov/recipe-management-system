package recipes.api;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import recipes.model.AppUser;
import recipes.service.UserDetailsServiceImpl;
import recipes.service.UserService;


@RestController
@RequestMapping("/api")
public class UserApi {

    @Autowired
    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    private UserService userService;

    public UserApi(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    @PostMapping(path = "/register")
    public void registerUser(@Valid @RequestBody AppUser user) {
        userService.registerUser(user);
    }


}

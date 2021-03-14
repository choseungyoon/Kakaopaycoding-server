package kakaopay.controller;

import kakaopay.entity.User;
import kakaopay.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/user")
    public User create() throws  Exception{
        return this.userService.create();
    }

}

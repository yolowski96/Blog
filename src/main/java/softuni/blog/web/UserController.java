package softuni.blog.web;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import softuni.blog.web.apiModel.UserCreateRegisterModel;
import softuni.blog.data.serviceModel.UserRegisterServiceModel;
import softuni.blog.service.UserService;
import softuni.blog.web.apiModel.UserResponseModel;

import java.util.List;


@RestController
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService,ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/login")
    public ResponseEntity<String> loginCon(){
        return ResponseEntity.ok().build();
    }

    @GetMapping("/register")
    public ResponseEntity<String> register() {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerConfirm(
            @RequestBody UserCreateRegisterModel userCreateRegisterModel){
        return this.userService.register(this.modelMapper.map(userCreateRegisterModel, UserRegisterServiceModel.class));
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserResponseModel>> getAllUsers(){
        return this.userService.findAllUsers();
    }

    @PostMapping("/set-admin/{username}")
    public ResponseEntity<String> makeAdmin(@PathVariable String username){
        return this.userService.setAdminRole(username);
    }

    @PostMapping("/set-user/{username}")
    public ResponseEntity<String> makeUser(@PathVariable String username){
        return this.userService.setUserRole(username);
    }

    @DeleteMapping("/delete/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable String username){
        return this.userService.removeUser(username);
    }
}

package softuni.blog.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import softuni.blog.data.entity.User;
import softuni.blog.data.serviceModel.UserRegisterServiceModel;
import softuni.blog.web.apiModel.UserResponseModel;

import java.util.List;

public interface UserService extends UserDetailsService {

    ResponseEntity<String> register(UserRegisterServiceModel userServiceModel);

    ResponseEntity<List<UserResponseModel>> findAllUsers();

    User findByUsername(String username);

    boolean isExistUsername(String username);

    boolean isExistEmail(String email);

    ResponseEntity<String> setAdminRole(String username);

    ResponseEntity<String> setUserRole(String username);

    ResponseEntity<String> removeUser(String username);
}

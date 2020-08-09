package softuni.blog.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softuni.blog.data.entity.User;
import softuni.blog.data.serviceModel.UserRegisterServiceModel;
import softuni.blog.data.serviceModel.UserServiceModel;
import softuni.blog.repository.RoleRepository;
import softuni.blog.repository.UserRepository;
import softuni.blog.service.UserService;
import softuni.blog.web.apiModel.UserResponseModel;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String s){
        User user = this.userRepository.findByUsername(s);
        if(user == null){
            throw new UsernameNotFoundException(s);
        }

        return org.springframework.security.core.userdetails.User.withUsername(user.getUsername()).password(user.getPassword()).authorities(user.getAuthorities()).build();
    }

    @Override
    public ResponseEntity<String> register(UserRegisterServiceModel userServiceModel) {

        User user = this.modelMapper.map(userServiceModel,User.class);

        if(isExistUsername(user.getUsername())){
            return ResponseEntity.status(HttpStatus.IM_USED).body("This username is already exists!");
        }else if(isExistEmail(user.getEmail())){
            return ResponseEntity.status(HttpStatus.IM_USED).body("This email is already exists!");
        }

        if(this.userRepository.count() == 0){
            user.setAuthorities(new HashSet<>(this.roleRepository.findAll()));
        }else{
            user.setAuthorities(new HashSet<>(Set.of(this.roleRepository.findByAuthority("ROLE_USER"))));
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        this.userRepository.saveAndFlush(user);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<List<UserResponseModel>> findAllUsers() {
        List<UserServiceModel> userServiceModel = this.userRepository.findAll()
                .stream()
                .map(u -> this.modelMapper.map(u, UserServiceModel.class))
                .collect(Collectors.toList());

        if(userServiceModel.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        List<UserResponseModel> responseModel = userServiceModel.stream()
                .map(u -> this.modelMapper.map(u, UserResponseModel.class))
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(responseModel);
    }

    @Override
    public User findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public boolean isExistUsername(String username) {
        return this.userRepository.existsUserByUsername(username);
    }

    @Override
    public boolean isExistEmail(String email) {
        return this.userRepository.existsUserByEmail(email);
    }

    @Override
    public ResponseEntity<String> setAdminRole(String username) {
        if(!isExistUsername(username)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User is not found!");
        }
        User user = this.userRepository.findByUsername(username);
        user.getAuthorities().clear();
        user.getAuthorities().add(this.roleRepository.findByAuthority("ROLE_USER"));
        user.getAuthorities().add(this.roleRepository.findByAuthority("ROLE_ADMIN"));

        this.userRepository.saveAndFlush(user);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<String> setUserRole(String username) {
        if(!isExistUsername(username)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User is not found!");
        }
        User user = this.userRepository.findByUsername(username);
        user.getAuthorities().clear();
        user.getAuthorities().add(this.roleRepository.findByAuthority("ROLE_USER"));

        this.userRepository.saveAndFlush(user);
        return ResponseEntity.ok().build();
    }

    @Override
    @Transactional
    public ResponseEntity<String> removeUser(String username) {
        if(!isExistUsername(username)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User is not found!");
        }
        User user = this.userRepository.findByUsername(username);
        this.userRepository.delete(user);
        return ResponseEntity.status(HttpStatus.OK).body("User is delete!");
    }
}
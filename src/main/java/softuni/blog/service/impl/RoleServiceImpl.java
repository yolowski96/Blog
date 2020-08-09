package softuni.blog.service.impl;

import org.springframework.stereotype.Service;
import softuni.blog.data.entity.Role;
import softuni.blog.repository.RoleRepository;
import softuni.blog.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void initRole() {
        if(this.roleRepository.count() == 0){
            Role root = new Role("ROLE_ROOT");
            Role admin = new Role("ROLE_ADMIN");
            Role user = new Role("ROLE_USER");

            this.roleRepository.saveAndFlush(root);
            this.roleRepository.saveAndFlush(admin);
            this.roleRepository.saveAndFlush(user);
        }
    }
}

package softuni.blog.data.serviceModel;

import javax.validation.constraints.NotNull;
import java.util.Set;

public class UserServiceModel {

    private String username;
    private Set<RoleServiceModel> authorities;

    public UserServiceModel() {
    }

    @NotNull
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotNull
    public Set<RoleServiceModel> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<RoleServiceModel> authorities) {
        this.authorities = authorities;
    }
}

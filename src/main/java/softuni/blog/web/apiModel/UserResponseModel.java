package softuni.blog.web.apiModel;

import softuni.blog.data.serviceModel.RoleServiceModel;

import java.util.Set;

public class UserResponseModel {

    private String username;
    private Set<RoleServiceModel> authorities;

    public UserResponseModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<RoleServiceModel> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<RoleServiceModel> authorities) {
        this.authorities = authorities;
    }
}

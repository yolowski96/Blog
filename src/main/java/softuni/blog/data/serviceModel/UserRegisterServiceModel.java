package softuni.blog.data.serviceModel;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Set;

public class UserRegisterServiceModel extends BaseServiceModel{

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private Set<RoleServiceModel> authorities;

    public UserRegisterServiceModel() {
    }

    @NotNull
    @Length(min = 2,max = 20,message = "Username length must be between 2 and 20 characters!")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotNull
    @Length(min = 3,max = 10,message = "Password must be between 3 and 10 characters")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotNull
    @Length(min = 2,max = 20,message = "First Name length must be between 2 and 20 characters!")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @NotNull
    @Length(min = 2,max = 20,message = "Last Name length must be between 2 and 20 characters!")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Email(message = "Enter valid email address")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<RoleServiceModel> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<RoleServiceModel> authorities) {
        this.authorities = authorities;
    }
}

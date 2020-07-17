package springadvanced.exam.user.domain.userEntity;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;

public class UserEntityBinding {

    private String username;
    private String password;
    private String confirmPassword;
    private String email;

    public UserEntityBinding() {
    }

    @Length(min = 3, message = "Username should not be at least 3 characters long!")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Length(min = 3, message = "Password should not be at least 3 characters long!")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Length(min = 3, message = "Password should not be at least 3 characters long!")
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Email(message = "Please enter valid Email!")
    @Length(min = 1, message = "Email can not be blank!")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}

package springadvanced.exam.user.domain.userEntity;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;

public class UserEntityUpdateBinding {

    private String id;
    private String username;
    private String oldPassword;
    private String newPassword;
    private String confirmNewPassword;
    private String email;

    public UserEntityUpdateBinding() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Length(min = 3, message = "Username should not be at least 3 characters long!")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Length(min = 3, message = "Password should not be at least 3 characters long!")
    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    @Length(min = 3, message = "Password should not be at least 3 characters long!")
    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Length(min = 3, message = "Password should not be at least 3 characters long!")
    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
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

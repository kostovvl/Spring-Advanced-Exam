package springadvanced.exam.user.domain.userRole;

import springadvanced.exam.utils.baseClasses.BaseDto;

public class UserRoleDto extends BaseDto {


    private String role;

    public UserRoleDto() {
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

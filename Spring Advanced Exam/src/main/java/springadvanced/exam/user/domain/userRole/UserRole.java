package springadvanced.exam.user.domain.userRole;

import springadvanced.exam.user.domain.userEntity.UserEntity;
import springadvanced.exam.utils.baseClasses.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "user_roles")
public class UserRole extends BaseEntity {

    private String role;
    private UserEntity user;

    public UserRole() {
    }

    public UserRole(String role) {
        this.role = role;
    }

    @Column(name = "role")
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @ManyToOne()
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}

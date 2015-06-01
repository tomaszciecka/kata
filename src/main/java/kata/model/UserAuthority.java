package kata.model;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;


@Embeddable
public class UserAuthority {

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    public UserAuthority() {

    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

}

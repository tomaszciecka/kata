package kata.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue
    private Long id;
    
    @NotNull
    @Column(unique = true)
    private String login;
    
    private boolean enabled;
    
    private UserAuthority authority;
    
    User() {
        
    }
    
    public User(String login,  boolean enabled) {
        this.login = login;
        this.enabled = enabled;
        
    }
    
    public String getLogin() {
        return login;
    }
    
    public UserAuthority getUserAuthority() {
        return authority;
    }

    
    public Long getId() {
        return id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (enabled ? 1231 : 1237);
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((login == null) ? 0 : login.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (enabled != other.enabled)
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (login == null) {
            if (other.login != null)
                return false;
        } else if (!login.equals(other.login))
            return false;
        return true;
    }
    
    

}

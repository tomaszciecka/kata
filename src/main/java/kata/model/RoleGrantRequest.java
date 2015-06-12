package kata.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "role_grants")
public class RoleGrantRequest {

    @Id
    @GeneratedValue
    private Long id;
    
    @OneToMany
    private Collection<User> grantingRepresentatives;
    
    @OneToOne
    private User user;
    
    private boolean granted;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;
    
    RoleGrantRequest() {
    }
    
    public RoleGrantRequest(User user, Organization organization) {
        this.grantingRepresentatives = new ArrayList<User>();
        this.granted = false;
        this.user = user;
        this.organization = organization;
    }

    public RoleGrantRequest(ArrayList<User> representatives, User user, boolean granted, Organization organization) {
        this.grantingRepresentatives = representatives;
        this.user = user;
        this.granted = granted;
        this.organization = organization;
    }

    
    public Collection<User> getGrantingRepresentatives() {
        if(grantingRepresentatives == null) {
            grantingRepresentatives = new ArrayList<User>();
        }
        return grantingRepresentatives;
    }

    
    public User getUser() {
        return user;
    }

    
    public boolean isGranted() {
        return granted;
    }
    
        
    public boolean canBeGrantedBy(User user) {
        return !isGranted() && !getGrantingRepresentatives().contains(user);
    }

    public void grantBy(User representative) {
        getGrantingRepresentatives().add(representative);
        if(getGrantingRepresentatives().size() >= organization.getGrantLimit()) {
            this.granted = true;
        }
    }
    
}

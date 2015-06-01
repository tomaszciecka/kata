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
    private Collection<User> representatives;
    
    @OneToOne
    private User user;
    
    private boolean granted;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    Organization organization;
    
    RoleGrantRequest() {
    }
    
    public RoleGrantRequest(User user, Organization organization) {
        this.representatives = new ArrayList<User>();
        this.granted = false;
        this.user = user;
        this.organization = organization;
    }

    public RoleGrantRequest(ArrayList<User> representatives, User user, boolean granted, Organization organization) {
        this.representatives = representatives;
        this.user = user;
        this.granted = granted;
        this.organization = organization;
    }

    
    public Collection<User> getRepresentatives() {
        if(representatives == null) {
            representatives = new ArrayList<User>();
        }
        return representatives;
    }

    
    public User getUser() {
        return user;
    }

    
    public boolean isGranted() {
        return granted;
    }
    
        
    public boolean canBeGranted(User user) {
        return !isGranted() && !representatives.contains(user);
    }

    public void grant(User representative) {
        representatives.add(representative);
        if(getRepresentatives().size() >= organization.getGrantLimit()) {
            this.granted = true;
        }
    }
    
}

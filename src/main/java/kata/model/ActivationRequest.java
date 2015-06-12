package kata.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "activation_requests")
public class ActivationRequest {

    @Id
    @GeneratedValue
    private Long id;
    
    @OneToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;
    
    private boolean activated;
    
    ActivationRequest() {

    }
    
    public ActivationRequest(Organization organization) {
        this.organization = organization;
        this.activated = false;
    }

    
    public Organization getOrganization() {
        return organization;
    }

    
    public boolean isActivated() {
        return activated;
    }

    
    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public Long getId() {
        return id;
    }
    
    
}

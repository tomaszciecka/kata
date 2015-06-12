package kata.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "documents")
public class Document {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    
    @ManyToOne
    private Organization organization;

    @ManyToOne
    private User owner;

    @OneToMany
    private Collection<User> signers;

    private boolean confirmed = false;

    Document() {

    }

    public Document(Organization organization, User owner, String name) {
        this.organization = organization;
        this.owner = owner;
        this.name = name;
    }

    public Organization getOrganization() {
        return organization;
    }

    public User getOwner() {
        return owner;
    }

    public Collection<User> getSigners() {
        if (signers == null) {
            signers = new ArrayList<User>();
        }
        return signers;
    }
    
    public String getName() {
        return name;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public void sign(User representative) {
        getSigners().add(representative);
        if (signers.size() >= organization.getSignLimit()) {
            confirmed = true;
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((organization == null) ? 0 : organization.hashCode());
        result = prime * result + ((owner == null) ? 0 : owner.hashCode());
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
        Document other = (Document) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (organization == null) {
            if (other.organization != null)
                return false;
        } else if (!organization.equals(other.organization))
            return false;
        if (owner == null) {
            if (other.owner != null)
                return false;
        } else if (!owner.equals(other.owner))
            return false;
        return true;
    }

    public Long getId() {
        return id;
    }

    
}

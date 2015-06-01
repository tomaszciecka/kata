package kata.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "organizations")
public class Organization {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Column(unique = true)
    private String name;

    @ManyToOne
    private User owner;

    @OneToMany
    private Collection<User> members;

    @OneToMany
    private Collection<User> representatives;

    @OneToMany(mappedBy = "organization")
    private Collection<RoleGrantRequest> roleGrantRequests;

    private boolean active;

    private Integer grantLimit = 0;

    private Integer signLimit = 0;
    
    Organization() {

    }

    public Organization(User owner, String name, Integer grantLimit, Integer signLimit) {
        this.owner = owner;
        this.name = name;
        this.active = false;
        this.grantLimit = grantLimit;
        this.signLimit = signLimit;
    }

    public Organization(String name2, User owner2, Collection<User> members2, Collection<User> representatives2, boolean active,
            Integer grantLimit, Integer signLimit) {
        this.owner = owner2;
        this.name = name2;
        this.active = active;
        this.members = members2;
        this.representatives = representatives2;
        this.grantLimit = grantLimit;
        this.signLimit = signLimit;
    }

    public Integer getGrantLimit() {
        return grantLimit;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public User getOwner() {
        return owner;
    }

    public Collection<User> getMembers() {
        if (members == null) {
            members = new ArrayList<User>();
        }
        return members;
    }

    public Collection<User> getRepresentatives() {
        if (representatives == null) {
            representatives = new ArrayList<User>();
        }
        return representatives;
    }

    public void addMember(User member) {
        if (members == null) {
            members = new ArrayList<User>();
        }
        members.add(member);
    }

    public void addRepresentative(User member) {
        if (representatives == null) {
            representatives = new ArrayList<User>();
        }
        representatives.add(member);
    }

    public Integer getSignLimit() {
        return signLimit;
    }

    public Long getId() {
        return id;
    }

    public boolean newRepresentativeCanBeAdded(User representative, User newRepresentative) {
        return isRepresentative(representative) && isMember(newRepresentative);
    }

    public boolean isOwner(User owner2) {
        return Objects.equals(owner, owner2);
    }

    public boolean isMember(User member) {
        return members.contains(member);
    }

    public boolean isRepresentative(User representative) {
        return representatives.contains(representative);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (active ? 1231 : 1237);
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((members == null) ? 0 : members.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((owner == null) ? 0 : owner.hashCode());
        result = prime * result + ((representatives == null) ? 0 : representatives.hashCode());
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
        Organization other = (Organization) obj;
        if (active != other.active)
            return false;
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
        if (owner == null) {
            if (other.owner != null)
                return false;
        } else if (!owner.equals(other.owner))
            return false;
        return true;
    }

    public void removeRepresentative(User user) {
        representatives.remove(user);
    }

}

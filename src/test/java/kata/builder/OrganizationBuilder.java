package kata.builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import kata.model.Organization;
import kata.model.User;


public class OrganizationBuilder extends BaseBuilder<Organization, OrganizationBuilder> {
    
    private String name;
    
    private User owner;
    
    private Collection<User> members = new ArrayList<>();
    
    private Collection<User> representatives = new ArrayList<>();
    
    private boolean active;
    
    private Integer grantLimit;
    
    private Integer signLimit;

    public static OrganizationBuilder organization() {
        return new OrganizationBuilder();
    }
    
    public OrganizationBuilder withName(String name) {
        this.name = name;
        return thisInstance();
    }
    
    public OrganizationBuilder withOwner(User owner) {
        this.owner = owner;
        return thisInstance();
    }
    
    public OrganizationBuilder isActive(boolean active) {
        this.active = active;
        return thisInstance();
    }
    
    public OrganizationBuilder withMembers(User... members) {
        this.members.addAll(Arrays.asList(members));
        return thisInstance();
    }
    
    public OrganizationBuilder withRepresentatives(User... representatives) {
        this.representatives.addAll(Arrays.asList(representatives));
        return thisInstance();
    }
    
    public OrganizationBuilder withGrantLimit(Integer grantLimit) {
        this.grantLimit = grantLimit;
        return thisInstance();
    }
    
    public OrganizationBuilder withSignLimit(Integer signLimit) {
        this.signLimit = signLimit;
        return thisInstance();
    }
    
    @Override
    public Organization build() {
        Organization organization = new Organization(name, owner, members, representatives, active, grantLimit, signLimit);
        return organization;
    }

}

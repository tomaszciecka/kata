package kata.service;

import static kata.builder.UserBuilder.user;
import static kata.builder.OrganizationBuilder.organization;
import kata.KataApplication;
import kata.exception.AuthorizationException;
import kata.model.ActivationRequest;
import kata.model.Organization;
import kata.model.RoleGrantRequest;
import kata.model.User;
import kata.repository.ActivationRequestRepository;
import kata.repository.OrganizationRepository;
import kata.repository.RoleGrantRequestRepository;
import kata.repository.UserRepository;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = KataApplication.class)
@Transactional
@WebAppConfiguration
public class OrganizationServiceTest {

    @Autowired
    OrganizationService organizationService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    OrganizationRepository organizationRepository;

    @Autowired
    ActivationRequestRepository activationRequestRepository;

    @Autowired
    RoleGrantRequestRepository roleGrantRequestRepository;

    User owner;

    @Before
    public void init() {
        owner = user().withLogin("test").enabled(true).build(userRepository);

    }

    @Test
    public void registersNewOrganization() {
        Organization newOrganization = organizationService.registerOrganization("NewOrgTest", owner.getId(), 0, 0);
        assertThat(newOrganization).isEqualTo(organizationService.getOrganizationByName("NewOrgTest"));
    }

    @Test
    public void sendsRequestToActivateOrganization() throws AuthorizationException {
        Organization inactiveOrganization = organization().withName("InactiveOrgTest").withOwner(owner).isActive(false)
                .build(organizationRepository);
        organizationService.requestToActivateOrganization(inactiveOrganization.getId(), owner.getId());
        assertThat(activationRequestRepository.findByOrganization(inactiveOrganization)).isNotNull();
    }

    @Test
    public void addsNewMemberToOrganization() {
        User member = user().withLogin("member").enabled(true).build(userRepository);
        Organization organization = organization().withName("OrgTest").withOwner(owner).isActive(true).build(organizationRepository);
        organizationService.addNewMember(member.getId(), owner.getId(), organization.getId());
        assertThat(organizationRepository.findByName("OrgTest").getMembers()).contains(member);
    }

    @Test
    public void addsNewRepresentativeToInactiveOrganization() {
        User representative = user().withLogin("member").enabled(true).build(userRepository);
        Organization organization = organization().withName("OrgTest").withOwner(owner).isActive(false).withMembers(representative)
                .build(organizationRepository);
        organizationService.addNewRepresentative(representative.getId(), owner.getId(), organization.getId());
        assertThat(organizationRepository.findByName("OrgTest").getRepresentatives()).contains(representative);
    }

    @Test
    public void addsNewRepresentativeRoleRequestToActiveOrganization() {
        User representative = user().withLogin("member").enabled(true).build(userRepository);
        Organization organization = organization().withName("OrgTest").withOwner(owner).isActive(true).withMembers(representative)
                .build(organizationRepository);
        organizationService.addNewRepresentative(representative.getId(), owner.getId(), organization.getId());
        assertThat(roleGrantRequestRepository.findByUser(representative)).isNotNull();
    }

    @Test
    public void grantsRoleRepresentative() {
        User representative = user().withLogin("representative").enabled(true).build(userRepository);
        User newRepresentative = user().withLogin("member").enabled(true).build(userRepository);
        Organization organization = organization().withName("OrgTest").withOwner(owner).isActive(true).withGrantLimit(1)
                .withMembers(representative, newRepresentative).withRepresentatives(representative).build(organizationRepository);
        roleGrantRequestRepository.save(new RoleGrantRequest(newRepresentative, organization));
        organizationService.grantRepresentative(representative.getId(), organization.getId(), newRepresentative.getId());
        assertThat(organizationRepository.findByName("OrgTest").isRepresentative(representative)).isTrue();
    }

    @Test
    public void revokesRole() {
        User representative = user().withLogin("representative").enabled(true).build(userRepository);
        Organization organization = organization().withName("OrgTest").withOwner(owner).isActive(true).withGrantLimit(1)
                .withMembers(representative).withRepresentatives(representative).build(organizationRepository);
        organizationService.revokeRole(owner.getId(), representative.getId(), organization.getId());
        assertThat(organizationRepository.findByName("OrgTest").isRepresentative(representative)).isFalse();
    }
    
    @Test
    public void activatesOrganization() throws AuthorizationException {
        Organization organization = organization().withName("OrgTest").withOwner(owner).isActive(false).build(organizationRepository);
        ActivationRequest activationRequest = organizationService.requestToActivateOrganization(organization.getId(), owner.getId());
        organizationService.activateOrganization(activationRequest.getId());
        assertThat(organizationRepository.findByName("OrgTest").isActive()).isTrue();
    }
}

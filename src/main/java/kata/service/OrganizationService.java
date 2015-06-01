package kata.service;

import java.util.Objects;

import kata.exception.AuthorizationException;
import kata.model.ActivationRequest;
import kata.model.Organization;
import kata.model.RoleGrantRequest;
import kata.model.User;
import kata.repository.ActivationRequestRepository;
import kata.repository.OrganizationRepository;
import kata.repository.RoleGrantRequestRepository;
import kata.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class OrganizationService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    OrganizationRepository organizationRepository;

    @Autowired
    ActivationRequestRepository activationRequestRepository;

    @Autowired
    RoleGrantRequestRepository roleGrantRequestRepository;

    public Organization getOrganizationByName(String name) {
        return organizationRepository.findByName(name);
    }

    public Organization addNewMember(Long userId, Long ownerId, Long organizationId) {
        User owner = userRepository.findOne(ownerId);
        Organization organization = organizationRepository.findOne(organizationId);
        if (Objects.equals(organization.getOwner(), owner)) {
            User member = userRepository.findOne(userId);
            organization.addMember(member);
        }
        return organization;
    }

    public Organization registerOrganization(String organizationName, Long ownerId, Integer grantLimit, Integer signLimit) {
        Organization organization = new Organization(userRepository.findOne(ownerId), organizationName, grantLimit, signLimit);
        return organizationRepository.save(organization);
    }

    public Organization addNewRepresentative(Long memberId, Long ownerId, Long organizationId) {
        User owner = userRepository.findOne(ownerId);
        Organization organization = organizationRepository.findOne(organizationId);
        if (organization.isOwner(owner)) {
            User newRepresentative = userRepository.findOne(memberId);
            if (organization.isMember(newRepresentative) && !organization.isOwner(newRepresentative)) {
                if (!organization.isActive()) {
                    organization.addRepresentative(newRepresentative);
                } else {
                    createNewGrantRequest(newRepresentative, organization);
                }
            }
        }
        return organization;
    }

    public Organization grantRepresentative(Long representativeId, Long organizationId, Long newRepresentativeId) {
        Organization organization = organizationRepository.findOne(organizationId);
        User representative = userRepository.findOne(representativeId);
        User newRepresentative = userRepository.findOne(newRepresentativeId);
        RoleGrantRequest roleGrantRequest = roleGrantRequestRepository.findByUser(newRepresentative);
        if (roleGrantRequest == null) {
            return organization;
        }
        if (organization.newRepresentativeCanBeAdded(representative, newRepresentative) && roleGrantRequest.canBeGranted(representative)) {
            roleGrantRequest.grant(representative);
            if (roleGrantRequest.isGranted()) {
                organization.addRepresentative(newRepresentative);
            }
        }
        return organization;
    }

    public ActivationRequest requestToActivateOrganization(Long organizationId, Long ownerId) throws AuthorizationException {
        User owner = userRepository.findOne(ownerId);
        Organization organization = organizationRepository.findOne(organizationId);
        if (Objects.equals(organization.getOwner(), owner)) {
            ActivationRequest activationRequest = new ActivationRequest(organization);
            return activationRequestRepository.save(activationRequest);
        } else {
            throw new AuthorizationException("User unauthorized to do this operation");
        }
        
    }
    
    public Iterable<ActivationRequest> getNonActivatedActivationRequests() {
        return activationRequestRepository.findByActivatedIsFalse();
    }

    public void activateOrganization(Long activationRequestId) {
        ActivationRequest activationRequest = activationRequestRepository.findOne(activationRequestId);
        activationRequest.getOrganization().setActive(true);
        activationRequest.setActivated(true);
        activationRequestRepository.save(activationRequest);
        
    }
    
    public void createNewGrantRequest(User newRepresentative, Organization organization) {
        RoleGrantRequest roleGrantRequest = new RoleGrantRequest(newRepresentative, organization);
        roleGrantRequestRepository.save(roleGrantRequest);
    }

    public void revokeRole(Long ownerId, Long userId, Long organizationId) {
        Organization organization = organizationRepository.findOne(organizationId);
        User owner = userRepository.findOne(ownerId);
        User user = userRepository.findOne(userId);
        if (organization.isOwner(owner)) {
            organization.removeRepresentative(user);
        }
    }
}

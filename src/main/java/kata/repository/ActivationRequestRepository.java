package kata.repository;

import kata.model.ActivationRequest;
import kata.model.Organization;

import org.springframework.data.repository.CrudRepository;


public interface ActivationRequestRepository extends CrudRepository<ActivationRequest, Long>{

    public ActivationRequest findByOrganization(Organization organization);

    public Iterable<ActivationRequest> findByActivatedIsFalse();
}

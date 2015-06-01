package kata.repository;


import kata.model.Organization;

import org.springframework.data.repository.CrudRepository;


public interface OrganizationRepository extends CrudRepository<Organization, Long> {

    Organization findByName(String name);

}

package kata.repository;

import kata.model.RoleGrantRequest;
import kata.model.User;

import org.springframework.data.repository.CrudRepository;


public interface RoleGrantRequestRepository extends CrudRepository<RoleGrantRequest, Long> {

    public RoleGrantRequest findByUser(User user);
}

package kata.repository;

import kata.model.User;

import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<User, Long>{

}

package kata.repository;

import kata.model.Document;

import org.springframework.data.repository.CrudRepository;


public interface DocumentRepository extends CrudRepository<Document, Long> {

    public Document findByName(String name);
}

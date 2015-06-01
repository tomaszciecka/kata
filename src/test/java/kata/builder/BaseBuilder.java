package kata.builder;

import org.springframework.data.repository.CrudRepository;


public abstract class BaseBuilder<E, B extends BaseBuilder<E, B>> {

    @SuppressWarnings("unchecked")
    protected B thisInstance() {
        return (B) this;
    }
    
    public abstract E build();
    
    public E build(CrudRepository<E, Long> repo) {
        E entity = build();
        repo.save(entity);
        return entity;
    }
}

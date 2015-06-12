package kata.builder;

import java.util.Arrays;
import java.util.Collection;
import kata.model.Document;
import kata.model.Organization;
import kata.model.User;


public class DocumentBuilder extends BaseBuilder<Document, DocumentBuilder> {
    
    private Organization organization;
    
    private User owner;
    
    private Collection<User> signers;
    
    private String name;
    
    private boolean confirmed;
    
    public static DocumentBuilder document() {
        return new DocumentBuilder();
    }
    
    public DocumentBuilder withOwner(User owner) {
        this.owner = owner;
        return thisInstance();
    }
    
    public DocumentBuilder confirmed(boolean confirmed) {
        this.confirmed = confirmed;
        return thisInstance();
    }
    
    public DocumentBuilder fromOrganization(Organization organization) {
        this.organization = organization;
        return thisInstance();
    }
    
    public DocumentBuilder withSigners(User... signers) {
        this.signers.addAll(Arrays.asList(signers));
        return thisInstance();
    }
    
    public DocumentBuilder withName(String name) {
        this.name = name;
        return thisInstance();
    }
    
    @Override
    public Document build() {
        Document document = new Document(organization, owner, name);
        document.setConfirmed(confirmed);
        
        return document;
    }

    
}

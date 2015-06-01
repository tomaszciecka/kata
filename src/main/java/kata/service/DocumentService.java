package kata.service;

import kata.model.Document;
import kata.model.Organization;
import kata.model.User;
import kata.repository.DocumentRepository;
import kata.repository.OrganizationRepository;
import kata.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class DocumentService {

    @Autowired
    UserRepository userRepository;
    
    @Autowired
    OrganizationRepository organizationRepository;
    
    @Autowired
    DocumentRepository documentRepository;
    
    public Document createNewDocument(Long ownerId, Long organizationId, String name) {
        User owner = userRepository.findOne(ownerId);
        Organization organization = organizationRepository.findOne(organizationId);
        if(organization.isActive() && organization.isRepresentative(owner)) {
            return documentRepository.save(new Document(organization, owner, name));
        }
        return null;
    }
    
    public Document signDocument(Long documentId, Long userId) {
        Document document = documentRepository.findOne(documentId);
        User representative = userRepository.findOne(userId);
        if(document.getOrganization().isRepresentative(representative)){
            document.sign(representative);
        }
        return document;
    }
}

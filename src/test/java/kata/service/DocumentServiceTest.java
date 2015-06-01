package kata.service;

import kata.KataApplication;
import kata.model.Document;
import kata.model.Organization;
import kata.model.User;
import kata.repository.DocumentRepository;
import kata.repository.OrganizationRepository;
import kata.repository.UserRepository;
import static kata.builder.UserBuilder.user;
import static kata.builder.OrganizationBuilder.organization;
import static kata.builder.DocumentBuilder.document;
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
public class DocumentServiceTest {
    
    private Integer SIGN_LIMIT = 1;

    @Autowired
    DocumentService documentService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    OrganizationRepository organizationRepository;

    @Autowired
    DocumentRepository documentRepository;

    User owner;
    User representative;

    Organization organization;

    @Before
    public void init() {
        owner = user().withLogin("test").build(userRepository);
        representative = user().withLogin("representative").enabled(true).build(userRepository);
        organization = organization().withName("OrgTest").withOwner(owner).withRepresentatives(representative).withSignLimit(SIGN_LIMIT).build(organizationRepository);
    }

    @Test
    public void createsNewDocument() {
        Document document = documentService.createNewDocument(owner.getId(), organization.getId(), "DocTest");
        assertThat(documentRepository.findByName("DocTest")).isEqualTo(document);
    }

    @Test
    public void signsDocument() {
        Document document = document().withName("DocTest").withOwner(owner).fromOrganization(organization).build(documentRepository);
        documentService.signDocument(document.getId(), representative.getId());
        assertThat(documentRepository.findByName("DocTest").getSigners()).contains(representative);
    }
}

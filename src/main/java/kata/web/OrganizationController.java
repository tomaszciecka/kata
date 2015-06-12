package kata.web;

import kata.dto.LimitDto;
import kata.exception.AuthorizationException;
import kata.service.OrganizationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/organizations")
public class OrganizationController {
    
    @Autowired
    OrganizationService organizationService;
    
    @RequestMapping(value = "", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @ResponseBody
    public Object registerOrganization(@RequestBody String organizationName, Long ownerId, LimitDto limitDto) {
        return organizationService.registerOrganization(organizationName, ownerId, limitDto);
    }

    @RequestMapping(value = "/newMember", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @ResponseBody
    public Object addNewMember(@RequestBody Long userId, Long ownerId, Long organizationId) {
        return organizationService.addNewMember(userId, ownerId, organizationId);
    }
    
    @RequestMapping(value = "/newRepresentative", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @ResponseBody
    public Object addNewRepresentative(@RequestBody Long memberId, Long ownerId, Long organizationId) {
        return organizationService.addNewRepresentative(memberId, ownerId, organizationId);
    }
            
    
    @RequestMapping(value = "/activationRequest", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @ResponseBody
    public Object requestToActivateOrganization(@RequestBody Long organizationId, Long ownerId) throws AuthorizationException {
        return organizationService.requestToActivateOrganization(organizationId, ownerId);
    }

}

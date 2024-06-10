package br.com.project.x.controller;

import br.com.project.x.domain.dto.ContactRequest;
import br.com.project.x.domain.dto.ContactResponse;
import br.com.project.x.service.ContactService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contact")
@Slf4j
@Api(tags = {"Contact"})
public class ContactController {

    @Autowired
    private ContactService contactService;

    @GetMapping
    public ContactResponse createContact(@RequestBody ContactRequest contact) {
        return contactService.insertContact(contact);
    }
    @GetMapping("/{value}")
    public ContactResponse findContatc(String value) {
        var response = contactService.findContactsByCpfOrEmail(value);

        return response;
    }

}

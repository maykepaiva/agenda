package br.com.project.x.controller;

import br.com.project.x.domain.dto.ContactRequest;
import br.com.project.x.domain.dto.ContactResponse;
import br.com.project.x.service.ContactService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/contact")
@Slf4j
@Api(tags = {"Contact"})
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping
    public ResponseEntity<ContactResponse> createContact(@RequestBody ContactRequest contact) {
        return ResponseEntity.status(CREATED).body(contactService.insertContact(contact));
    }

    @GetMapping("/{value}")
    public ResponseEntity<ContactResponse> findContatc(@PathVariable String value) {
        return ResponseEntity.status(OK).body(contactService.findContactsByCpfOrName(value));
    }

    @GetMapping
    public ResponseEntity<Page<ContactResponse>> findAllUser(Pageable pageable) {
        return ResponseEntity.status(OK).body(contactService.findAllByOrderByNameAsc(pageable));
    }

    @DeleteMapping
    public String deleteContact(@RequestParam(required = true) String value, @RequestParam(required = true) String password) {
        return contactService.deleteContactByNomeOrCpf(value, password);
    }
}

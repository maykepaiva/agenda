package br.com.project.x.util;

import br.com.project.x.domain.dto.ContactResponse;
import br.com.project.x.domain.entity.Contact;
import org.springframework.stereotype.Service;

@Service
public class ContactMapper {
    public ContactResponse toDTO(Contact contact) {
        ContactResponse dto = new ContactResponse();
        dto.setUsuarioId(contact.getUser().getId());
        dto.setNome(contact.getNome());
        dto.setCpf(contact.getCPF());
        dto.setCep(contact.getCEP());
        dto.setEndereco(contact.getEndereco());
        dto.setComplemento(contact.getComplemento());
        dto.setTelefone(contact.getTelefone());
        dto.setLatitude(contact.getLatitude());
        dto.setLongitude(contact.getLongitude());
        return dto;
    }

    public static Contact toEntity(ContactResponse dto) {
        Contact contact = new Contact();
        contact.setId(dto.getUsuarioId());
        contact.setNome(dto.getNome());
        contact.setCPF(dto.getCpf());
        contact.setTelefone(dto.getTelefone());
        contact.setEndereco(dto.getEndereco());
        contact.setComplemento(dto.getComplemento());
        contact.setCEP(dto.getCep());
        contact.setLatitude(dto.getLatitude());
        contact.setLongitude(dto.getLongitude());
        return contact;
    }
}

package br.com.project.x.service;

import br.com.project.x.config.SecurityFilter;
import br.com.project.x.domain.dto.ContactRequest;
import br.com.project.x.domain.dto.ContactResponse;
import br.com.project.x.domain.dto.GeocodingResponse;
import br.com.project.x.domain.entity.Contact;
import br.com.project.x.domain.entity.User;
import br.com.project.x.repository.ContactRepository;
import br.com.project.x.repository.UserRepository;
import br.com.project.x.util.ContactMapper;
import br.com.project.x.util.CpfValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class ContactService {
    @Value("${google.maps.api.key}")
    private String apiKey;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private CpfValidator cpfValidator;
    @Autowired
    private SecurityFilter securityFilter;
    @Autowired
    private ContactMapper contactMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;


    private final RestTemplate restTemplate = new RestTemplate();
    private final ModelMapper mapper = new ModelMapper();

    public ContactResponse insertContact(ContactRequest request) {
        if (!cpfValidator.validarCPF(request.getCpf())) throw new RuntimeException("CPF Inválido!!!");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = securityFilter.getLoggedUsername();
        Optional<User> usuarioOptional = userRepository.findByEmail(userEmail);
        if (usuarioOptional.isPresent()) {
            if (usuarioOptional.get().getContatos().stream().anyMatch(contact -> contact.getCPF().equals(request.getCpf())))
                throw new RuntimeException("Contato já cadastrado com esse CPF");
        }
        User user = usuarioOptional.orElseThrow(() -> new RuntimeException("Usuario não encontrado"));
        getLatLongFromCep(request);
        Contact contactEntity = mapper.map(request, Contact.class);
        contactEntity.setUser(user);
        ContactResponse response = (mapper.map(contactRepository.save(contactEntity), ContactResponse.class));
        response.setUsuarioId(user.getId());
        return response;
    }

    public void getLatLongFromCep(ContactRequest contact) {
        String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" +
                contact.getCep() + "&key=" + apiKey;

        GeocodingResponse response = restTemplate.getForObject(url, GeocodingResponse.class);

        if (response != null && !response.getResults().isEmpty()) {
            var results = response.getResults().get(0);
            contact.setLatitude(results.getGeometry().getLocation().getLat());
            contact.setLongitude(results.getGeometry().getLocation().getLng());
        } else {
            throw new RuntimeException("Não foi possível obter a latitude e longitude para o CEP: " + contact.getCep());
        }
    }

    public ContactResponse findContactsByCpfOrName(String value) {
        String userEmail = securityFilter.getLoggedUsername();
        Optional<User> userOptional = userRepository.findByEmail(userEmail);
        if (userOptional.isPresent()) {
            Optional<Contact> contatoEncontrado = userOptional.get().getContatos().stream()
                    .filter(contact -> contact.getCPF().equals(value) || contact.getNome().equals(value)).findFirst();
            if (contatoEncontrado.isPresent()) {
                ContactResponse response = mapper.map(contatoEncontrado.get(), ContactResponse.class);
                response.setUsuarioId(userOptional.get().getId());
                return response;
            } else {
                throw new RuntimeException("Contato não encontrado!!!");
            }
        } else {
            throw new RuntimeException("Contato não encontrado!!!");
        }
    }

    public Page<ContactResponse> findAllByOrderByNameAsc(Pageable pageable) {
        String userEmail = securityFilter.getLoggedUsername();
        Optional<User> userOptional = userRepository.findByEmail(userEmail);
        if (userOptional.isPresent()) {
            Page<Contact> contactsPage = contactRepository.findByUserId(userOptional.get().getId(), pageable);
            return contactsPage.map(contactMapper::toDTO);
        }
        return Page.empty(pageable);
    }

    public String deleteContactByNomeOrCpf(String value, String password) {
        String userEmail = securityFilter.getLoggedUsername();
        Optional<User> userOptional = userRepository.findByEmail(userEmail);
        if (userOptional.isPresent()) {
            if (passwordEncoder.matches(password, userOptional.get().getPassword())) {
                Optional<Contact> contatoEncontrado = userOptional.get().getContatos().stream()
                        .filter(contact -> contact.getCPF().equals(value) || contact.getNome().equals(value)).findFirst();
                if (contatoEncontrado.isPresent()) {
                    contactRepository.deleteById(contatoEncontrado.get().getId());
                    userOptional.get().getContatos().remove(contatoEncontrado.get());
                    userRepository.save(userOptional.get());
                    return "Contato " + contatoEncontrado.get().getNome() + " excluído com sucesso";
                } else {
                    return "Contato não encontrado";
                }
            } else {
                return "Senha incorreta";
            }
        } else {
            return "Usuário não encontrado";
        }
    }
}

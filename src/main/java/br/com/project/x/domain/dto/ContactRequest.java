package br.com.project.x.domain.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactRequest {

    private Long idContact;
    private String nome;
    private String cpf;
    private String telefone;
    private String endereco;
    private String cep;
    private double latitude;
    private double longitude;

}
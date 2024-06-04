package br.com.project.x.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@Table(name = "TB_CONTACT")
@AllArgsConstructor
@NoArgsConstructor
public class Contact {

    @Id
    private Long contactId;
    private String nome;
    private String cpf;
    private String telefone;
    private String endereco;
    private String cep;
    private double latitude;
    private double longitude;
    private Long usuarioId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

}
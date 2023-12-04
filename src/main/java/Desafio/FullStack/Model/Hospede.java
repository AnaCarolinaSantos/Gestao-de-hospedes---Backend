package Desafio.FullStack.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name="hospedes")
@Data
public class Hospede {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private Date dt_nascimento;

    private String cpf;

    private String telefone;

    private String email;

}

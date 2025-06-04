package projetojavavscode.safeflame.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Endereco {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 4, message = "o logradouro deve ter no mínimo 4 caracteres")
    @NotBlank(message = "o campo logradouro é obrigatório")
    private String logradouro;

    @Size(min = 3, message = "o bairro deve ter no mínimo 3 caracteres")
    @NotBlank(message = "o campo bairro é obrigatório")
    private String bairro;

    @Size(min = 4, message = "a cidade deve ter no mínimo 4 caracteres")
    @NotBlank(message = "o campo cidade é obrigatório")
    private String cidade;

    @Size(min = 6, max = 32, message = "o estado deve ter entre 6 a 32 caracteres")
    @NotBlank(message = "o campo estado é obrigatório")
    private String estado;

    @Size(min = 9, max = 9, message = "o cep deve ter 9 caracteres")
    @NotBlank(message = "o campo cep é obrigatório")
    @Column(unique = true)
    private String cep;
}

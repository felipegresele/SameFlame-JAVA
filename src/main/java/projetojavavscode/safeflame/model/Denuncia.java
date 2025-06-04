package projetojavavscode.safeflame.model;

import java.time.LocalDate;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Denuncia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 5, max = 24, message = "o nome deve ter entre 5 e 24 caracteres")
    @NotBlank(message = "o campo nome é obrigatório")
    private String nome;

    @Size(min = 10, max = 150, message = "a descrição deve ter entre 10 e 150 caracteres")
    @NotBlank(message = "o campo descrição é obrigatório")
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "endereco_id")
    @NotNull(message = "o campo endereço é obrigatório")
    private Endereco endereco;

    private LocalDate data;

    @PrePersist
    public void prePersist() {
        this.data = LocalDate.now();
    }
}

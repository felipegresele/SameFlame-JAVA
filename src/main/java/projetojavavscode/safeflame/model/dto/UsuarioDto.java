package projetojavavscode.safeflame.model.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import projetojavavscode.safeflame.model.CargoUsuario;
import projetojavavscode.safeflame.model.Endereco;

@Data
public class UsuarioDto {

    @Size(min = 8, max = 64, message = "O nome deve ter entre 8 a 64 caracteres")
    @NotBlank(message = "O campo nome é obrigatório")
    private String nomeCompleto;

    @Email(message = "E-mail inválido")
    @NotBlank(message = "O campo e-mail é obrigatório")
    private String email;

    @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres")
    @NotBlank(message = "O campo senha é obrigatório")
    private String password;

    @Enumerated(EnumType.STRING)
    private CargoUsuario cargo;

    @NotNull(message = "O campo endereço é obrigatório")
    private Endereco endereco;
}

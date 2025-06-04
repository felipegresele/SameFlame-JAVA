package projetojavavscode.safeflame.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Usuario implements UserDetails {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 8, max = 64, message = "o nome deve ter entre 8 a 64 caracteres")
    @NotBlank(message = "o campo nome é obrigatório")
    private String nomeCompleto;

    @Email(message = "e-mail inválido")
    @NotBlank(message = "o campo email é obrigatório")
    @Column(unique = true)
    private String email;

    @Size(min = 6, message = "deve ter pelo menos 6 caracteres")
    private String password;

    @Enumerated(EnumType.STRING)
    private CargoUsuario cargo;

    @ManyToOne
    @JoinColumn(name = "endereco_id")
    @NotNull(message = "o campo endereço é obrigatório")
    private Endereco endereco;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(cargo.toString()));
    }

    @Override
    public String getUsername() {
        return email;
    }
}

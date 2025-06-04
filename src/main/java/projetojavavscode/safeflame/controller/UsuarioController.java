package projetojavavscode.safeflame.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.data.domain.Pageable;
import projetojavavscode.safeflame.model.Endereco;
import projetojavavscode.safeflame.model.Usuario;
import projetojavavscode.safeflame.model.dto.UsuarioDto;
import projetojavavscode.safeflame.repository.EnderecoRepository;
import projetojavavscode.safeflame.repository.UsuarioRepository;
import projetojavavscode.safeflame.specification.UsuarioSpecification;

@RestController
@RequestMapping("/usuarios")
@Slf4j
public class UsuarioController {

    public record UsuarioFilter(String nomeCompleto, String email) {}

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public Page<Usuario> index(UsuarioFilter filtro,
                               @PageableDefault(size = 10, sort = "nomeCompleto", direction = Direction.ASC) Pageable pageable) {

        var specification = UsuarioSpecification.withFilters(filtro);

        return repository.findAll(specification, pageable);
    }

    @GetMapping("{id}")
    public Usuario get(@PathVariable Long id) {
        log.info("buscando usuario " + id);
        return getUsuario(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Usuario post(@RequestBody @Valid UsuarioDto dto) {
        log.info("cadastrando usuario " + dto.getEmail());

        Endereco endereco = Endereco.builder()
                .logradouro(dto.getEndereco().getLogradouro())
                .bairro(dto.getEndereco().getBairro())
                .estado(dto.getEndereco().getEstado())
                .cidade(dto.getEndereco().getCidade())
                .cep(dto.getEndereco().getCep())
                .build();

        enderecoRepository.save(endereco);

        Usuario usuario = Usuario.builder()
                .nomeCompleto(dto.getNomeCompleto())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .endereco(endereco)
                .cargo(dto.getCargo())
                .build();

        return repository.save(usuario);
    }

    @PutMapping("{id}")
    public Usuario update(@PathVariable Long id, @RequestBody @Valid Usuario usuario) {
        log.info("atualizando usuario " + id + " para " + usuario);
        usuario.setId(id);

        return repository.save(usuario);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        log.info("deletando usuario " + id);
        repository.delete(getUsuario(id));
    }

    private Usuario getUsuario(Long id) {
        return repository
                .findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
                );
    }
}

package projetojavavscode.safeflame.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;
import projetojavavscode.safeflame.model.Endereco;
import projetojavavscode.safeflame.repository.EnderecoRepository;

@RestController
@RequestMapping("/enderecos")
@Slf4j
public class EnderecoController {

    @Autowired
    private EnderecoRepository repository;

    @GetMapping
    public Page<Endereco> index(
            @PageableDefault(size = 10, sort = "cep", direction = Direction.ASC) Pageable pageable) {

        return repository.findAll(pageable);
    }

    @GetMapping("{id}")
    public Endereco get(@PathVariable Long id) {
        log.info("buscando endereco " + id);
        return getEndereco(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Endereco post(@RequestBody @Valid Endereco endereco) {
        log.info("registrando endereco " + endereco);
        return repository.save(endereco);
    }

    @PutMapping("{id}")
    public Endereco update(@RequestBody @Valid Endereco endereco, @PathVariable Long id) {
        log.info("atualizando endereco " + id + " para " + endereco);
        endereco.setId(id);

        return repository.save(endereco);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        log.info("deletando endereco " + id);

        repository.delete(getEndereco(id));
    }

    private Endereco getEndereco(Long id) {
        return repository
                .findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
                );
    }

}

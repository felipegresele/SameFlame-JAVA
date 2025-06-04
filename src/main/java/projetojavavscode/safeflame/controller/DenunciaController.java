package projetojavavscode.safeflame.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import projetojavavscode.safeflame.model.Denuncia;
import org.springframework.data.web.PageableDefault;
import projetojavavscode.safeflame.model.Endereco;
import projetojavavscode.safeflame.model.dto.DenunciaDto;
import projetojavavscode.safeflame.repository.DenunciaRepository;
import projetojavavscode.safeflame.repository.EnderecoRepository;
import org.springframework.data.domain.Sort.Direction;

@RestController
@RequestMapping("/denuncias")
@Slf4j
public class DenunciaController {

@Autowired
    private DenunciaRepository repository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @GetMapping
    public Page<Denuncia> index(
            @PageableDefault(size = 10, sort = "nome", direction = Direction.ASC) Pageable pageable) {

        return repository.findAll(pageable);
    }

    @GetMapping("{id}")
    public Denuncia get(@PathVariable Long id) {
        log.info("buscando denuncia " + id);
        return getDenuncia(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Denuncia post(@RequestBody @Valid DenunciaDto dto) {
        log.info("registrando denuncia " + dto);

        Endereco endereco = Endereco.builder()
                .logradouro(dto.getEndereco().getLogradouro())
                .bairro(dto.getEndereco().getBairro())
                .cidade(dto.getEndereco().getCidade())
                .estado(dto.getEndereco().getEstado())
                .cep(dto.getEndereco().getCep())
                .build();

        enderecoRepository.save(endereco);

        Denuncia denuncia = Denuncia.builder()
                .nome(dto.getNome())
                .descricao(dto.getDescricao())
                .endereco(endereco)
                .build();

        return repository.save(denuncia);
    }

    @PutMapping("{id}")
    public Denuncia update(@RequestBody @Valid DenunciaDto dto, @PathVariable Long id) {
        log.info("atualizando denuncia " + id + " para " + dto);

        Denuncia denunciaExistente = getDenuncia(id);

        Endereco enderecoAtualizado = Endereco.builder()
                .id(denunciaExistente.getEndereco().getId()) // mantém o id para atualizar o endereço existente
                .logradouro(dto.getEndereco().getLogradouro())
                .bairro(dto.getEndereco().getBairro())
                .cidade(dto.getEndereco().getCidade())
                .estado(dto.getEndereco().getEstado())
                .cep(dto.getEndereco().getCep())
                .build();

        enderecoRepository.save(enderecoAtualizado);

        denunciaExistente.setNome(dto.getNome());
        denunciaExistente.setDescricao(dto.getDescricao());
        denunciaExistente.setEndereco(enderecoAtualizado);

        return repository.save(denunciaExistente);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        log.info("deletando denuncia " + id);

        repository.delete(getDenuncia(id));
    }

    private Denuncia getDenuncia(Long id) {
        return repository
                .findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
                );
    }
}

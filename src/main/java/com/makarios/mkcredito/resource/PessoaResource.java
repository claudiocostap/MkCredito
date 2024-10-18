package com.makarios.mkcredito.resource;

import com.makarios.mkcredito.model.Pessoa;
import com.makarios.mkcredito.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pessoas")
public class PessoaResource {

    @Autowired
    private PessoaService pessoaService;

    // Retorna a lista de todas as pessoas
    @GetMapping
    public ResponseEntity<List<Pessoa>> listarTodas() {
        List<Pessoa> pessoas = pessoaService.buscarTodas();
        if (pessoas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pessoas);
    }

    // Busca uma pessoa específica pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> buscarPorId(@PathVariable Long id) {
        Optional<Pessoa> pessoa = pessoaService.buscarPorId(id);
        return pessoa.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Cria uma nova pessoa no sistema
    @PostMapping
    public ResponseEntity<Pessoa> criar(@RequestBody Pessoa pessoa) {
        Optional<Pessoa> pessoaExistente = pessoaService.buscarPorCpf(pessoa.getCpf());
        if (!pessoaExistente.isPresent()){
        Pessoa novaPessoa = pessoaService.salvar(pessoa);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaPessoa);
        }
        else return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).build();
    }

    // Atualiza uma pessoa existente
    @PutMapping("/{id}")
    public ResponseEntity<Pessoa> atualizar(@PathVariable Long id, @RequestBody Pessoa pessoa) {
        Optional<Pessoa> pessoaExistente = pessoaService.buscarPorId(id);

        if (!pessoaExistente.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Pessoa pessoaAtualizada = pessoaService.atualizar(id, pessoa);
        return ResponseEntity.ok(pessoaAtualizada);
    }

    // Remove uma pessoa do sistema
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        Optional<Pessoa> pessoaExistente = pessoaService.buscarPorId(id);

        if (!pessoaExistente.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        pessoaService.remover(id);
        return ResponseEntity.noContent().build();
    }
}

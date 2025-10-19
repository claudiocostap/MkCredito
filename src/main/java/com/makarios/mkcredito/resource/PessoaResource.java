package com.makarios.mkcredito.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.makarios.mkcredito.event.RecursoCriadoEvent;
import com.makarios.mkcredito.exceptionhandler.PessoaNotFoundException;
import com.makarios.mkcredito.model.Pessoa;
import com.makarios.mkcredito.service.PessoaService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/pessoas")
public class PessoaResource {

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private ApplicationEventPublisher publisher;

    // 🔹 Listar todas as pessoas com paginação
    @GetMapping
    public ResponseEntity<Page<Pessoa>> listarTodas(Pageable pageable) {
        Page<Pessoa> pessoas = pessoaService.buscarTodas(pageable);
        return pessoas.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(pessoas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> buscarPorId(@PathVariable Long id) {
        return pessoaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new PessoaNotFoundException(id));
    }

    @PostMapping
    public ResponseEntity<Pessoa> criar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
        Pessoa novaPessoa = pessoaService.salvar(pessoa);

        publisher.publishEvent(new RecursoCriadoEvent(this, response, novaPessoa.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(novaPessoa); // O listener já define o status, mas mantê-lo aqui é uma boa prática.
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pessoa> atualizar(@PathVariable Long id, @Valid @RequestBody Pessoa pessoa) {
        Pessoa pessoaAtualizada = pessoaService.atualizar(id, pessoa);
        return ResponseEntity.ok(pessoaAtualizada);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        pessoaService.remover(id);
    }
}

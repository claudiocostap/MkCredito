package com.makarios.mkcredito.resource;

import com.makarios.mkcredito.model.PontoEletronico;
import com.makarios.mkcredito.service.PontoEletronicoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/ponto-eletronico")
public class PontoEletronicoResource {

    private final PontoEletronicoService pontoEletronicoService;

    public PontoEletronicoResource(PontoEletronicoService pontoEletronicoService) {
        this.pontoEletronicoService = pontoEletronicoService;
    }

    // Listar todos os pontos eletrônicos
    @GetMapping
    public ResponseEntity<List<PontoEletronico>> listarTodos() {
        List<PontoEletronico> pontosEletronicos = pontoEletronicoService.listarTodos();
        return ResponseEntity.ok(pontosEletronicos);
    }

    // Buscar ponto eletrônico por ID
    @GetMapping("/{id}")
    public ResponseEntity<PontoEletronico> buscarPorId(@PathVariable Long id) {
        PontoEletronico pontoEletronico = pontoEletronicoService.buscarPorId(id);
        return ResponseEntity.ok(pontoEletronico);
    }

    // Criar um novo ponto eletrônico
    @PostMapping
    public ResponseEntity<PontoEletronico> salvar(@Valid @RequestBody PontoEletronico pontoEletronico) {
        PontoEletronico novoPontoEletronico = pontoEletronicoService.salvar(pontoEletronico);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoPontoEletronico);
    }

    // Atualizar um ponto eletrônico existente
    @PutMapping("/{id}")
    public ResponseEntity<PontoEletronico> atualizar(@PathVariable Long id, @Valid @RequestBody PontoEletronico pontoEletronico) {
        PontoEletronico pontoAtualizado = pontoEletronicoService.atualizar(id, pontoEletronico);
        return ResponseEntity.ok(pontoAtualizado);
    }

    // Deletar um ponto eletrônico por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        pontoEletronicoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

package com.makarios.mkcredito.resource;

import com.makarios.mkcredito.model.PontoEletronico;
import com.makarios.mkcredito.service.PontoEletronicoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // Listar pontos por funcionário
    @GetMapping("/funcionario/{funcionarioId}")
    public ResponseEntity<List<PontoEletronico>> listarPorFuncionario(@PathVariable Long funcionarioId) {
        List<PontoEletronico> pontos = pontoEletronicoService.listarPorFuncionario(funcionarioId);
        return ResponseEntity.ok(pontos);
    }

    // Registrar entrada
    @PostMapping("/entrada/{funcionarioId}")
    public ResponseEntity<PontoEletronico> registrarEntrada(@PathVariable Long funcionarioId) {
        PontoEletronico novoPonto = pontoEletronicoService.registrarEntrada(funcionarioId);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoPonto);
    }

    // Registrar saída
    @PostMapping("/saida/{funcionarioId}")
    public ResponseEntity<PontoEletronico> registrarSaida(@PathVariable Long funcionarioId) {
        PontoEletronico pontoAtualizado = pontoEletronicoService.registrarSaida(funcionarioId);
        return ResponseEntity.ok(pontoAtualizado);
    }

    // Adicionar justificativa
    @PutMapping("/{id}/justificativa")
    public ResponseEntity<PontoEletronico> adicionarJustificativa(@PathVariable Long id, @RequestBody String justificativa) {
        PontoEletronico pontoAtualizado = pontoEletronicoService.adicionarJustificativa(id, justificativa);
        return ResponseEntity.ok(pontoAtualizado);
    }

    // Deletar um ponto eletrônico por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        pontoEletronicoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

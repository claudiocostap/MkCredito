package com.makarios.mkcredito.resource;

import com.makarios.mkcredito.dto.ResumoPontoDTO;
import com.makarios.mkcredito.model.PontoEletronico;
import com.makarios.mkcredito.service.PontoEletronicoService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
    public ResponseEntity<PontoEletronico> registrarEntrada(@PathVariable Long funcionarioId, @RequestParam Long usuarioId) {
        PontoEletronico novoPonto = pontoEletronicoService.registrarEntrada(funcionarioId, usuarioId);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoPonto);
    }

    // Registrar saída
    @PostMapping("/saida/{funcionarioId}")
    public ResponseEntity<PontoEletronico> registrarSaida(@PathVariable Long funcionarioId, @RequestParam Long usuarioId) {
        PontoEletronico pontoAtualizado = pontoEletronicoService.registrarSaida(funcionarioId, usuarioId);
        return ResponseEntity.ok(pontoAtualizado);
    }

    // Adicionar justificativa
    @PutMapping("/{id}/justificativa")
    public ResponseEntity<PontoEletronico> adicionarJustificativa(@PathVariable Long id, @RequestBody String justificativa, @RequestParam Long usuarioId) {
        PontoEletronico pontoAtualizado = pontoEletronicoService.adicionarJustificativa(id, justificativa, usuarioId);
        return ResponseEntity.ok(pontoAtualizado);
    }

    // Deletar um ponto eletrônico por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        pontoEletronicoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/resumo/{funcionarioId}")
    public ResponseEntity<ResumoPontoDTO> obterResumoHoras(
            @PathVariable Long funcionarioId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        ResumoPontoDTO resumo = pontoEletronicoService.calcularResumoHoras(funcionarioId, inicio, fim);
        return ResponseEntity.ok(resumo);
    }
}

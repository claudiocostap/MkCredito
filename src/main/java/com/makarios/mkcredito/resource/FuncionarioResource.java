package com.makarios.mkcredito.resource;

import com.makarios.mkcredito.model.Funcionario;
import com.makarios.mkcredito.service.FuncionarioService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioResource {

    private final FuncionarioService funcionarioService;

    public FuncionarioResource(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    // 1️⃣ Listar todos os funcionários (com paginação)
    @GetMapping
    public ResponseEntity<Page<Funcionario>> listarTodos(Pageable pageable) {
        Page<Funcionario> funcionarios = funcionarioService.listarPaginado(pageable);
        return ResponseEntity.ok(funcionarios);
    }

    // 2️⃣ Buscar funcionário por ID
    @GetMapping("/{id}")
    public ResponseEntity<Funcionario> buscarPorId(@PathVariable Long id) {
        Funcionario funcionario = funcionarioService.buscarPorId(id);
        return ResponseEntity.ok(funcionario);
    }

    // 3️⃣ Criar novo funcionário (com validação do corpo da requisição)
    @PostMapping
    public ResponseEntity<Funcionario> salvar(@Valid @RequestBody Funcionario funcionario) {
        Funcionario funcionarioSalvo = funcionarioService.salvar(funcionario);
        return ResponseEntity.status(HttpStatus.CREATED).body(funcionarioSalvo);
    }

    // 4️⃣ Atualizar funcionário por ID (com validação do corpo da requisição)
    @PutMapping("/{id}")
    public ResponseEntity<Funcionario> atualizar(@PathVariable Long id, @Valid @RequestBody Funcionario funcionario) {
        Funcionario funcionarioAtualizado = funcionarioService.atualizar(id, funcionario);
        return ResponseEntity.ok(funcionarioAtualizado);
    }

    // 5️⃣ Deletar funcionário por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        funcionarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // 6️⃣ Buscar funcionários por cargo
    @GetMapping("/cargo")
    public ResponseEntity<List<Funcionario>> buscarPorCargo(@RequestParam String cargo) {
        List<Funcionario> funcionarios = funcionarioService.buscarPorCargo(cargo);
        return ResponseEntity.ok(funcionarios);
    }

    // 7️⃣ Buscar funcionários com salário maior que um valor
    @GetMapping("/salario-maior-que")
    public ResponseEntity<List<Funcionario>> buscarPorSalarioMaiorQue(@RequestParam BigDecimal salario) {
        List<Funcionario> funcionarios = funcionarioService.buscarPorSalarioMaiorQue(salario);
        return ResponseEntity.ok(funcionarios);
    }

}

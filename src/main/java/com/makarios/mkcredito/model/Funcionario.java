package com.makarios.mkcredito.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "funcionario")
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "pessoa_id", nullable = false) // Garantir que o relacionamento não seja nulo
    @NotNull(message = "O campo pessoa é obrigatório.")
    private Pessoa pessoa;

    @Column(nullable = false)
    @NotBlank(message = "O campo cargo não pode estar vazio.")
    private String cargo;

    @Column(nullable = false, precision = 10, scale = 2) // Controlar precisão e escala do BigDecimal
    @NotNull(message = "O campo salário é obrigatório.")
    private BigDecimal salario;

    @Column(name = "data_contratacao", nullable = false)
    @NotNull(message = "A data de contratação é obrigatória.")
    private LocalDate dataContratacao;

    // Construtor padrão (necessário para o JPA)
    public Funcionario() {}

    // Construtor parametrizado
    public Funcionario(Long id, Pessoa pessoa, String cargo, BigDecimal salario, LocalDate dataContratacao) {
        this.id = id;
        this.pessoa = pessoa;
        this.cargo = cargo;
        this.salario = salario;
        this.dataContratacao = dataContratacao;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public LocalDate getDataContratacao() {
        return dataContratacao;
    }

    public void setDataContratacao(LocalDate dataContratacao) {
        this.dataContratacao = dataContratacao;
    }

    // equals() e hashCode()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Funcionario that = (Funcionario) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(pessoa, that.pessoa) &&
                Objects.equals(cargo, that.cargo) &&
                Objects.equals(salario, that.salario) &&
                Objects.equals(dataContratacao, that.dataContratacao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pessoa, cargo, salario, dataContratacao);
    }

    @Override
    public String toString() {
        return "Funcionario{" +
                "id=" + id +
                ", pessoa=" + pessoa +
                ", cargo='" + cargo + '\'' +
                ", salario=" + salario +
                ", dataContratacao=" + dataContratacao +
                '}';
    }
}

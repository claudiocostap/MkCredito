package com.makarios.mkcredito.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "funcionario")
public class Funcionario extends Pessoa {
    @Column(nullable = false)
    private String cargo;

    @Column(nullable = false)
    private BigDecimal salario;

    @Column(name = "data_contratacao", nullable = false)
    private LocalDate dataContratacao;

    // Getters e Setters
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!super.equals(o)) return false;
        Funcionario that = (Funcionario) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(cargo, that.cargo) &&
                Objects.equals(salario, that.salario) &&
                Objects.equals(dataContratacao, that.dataContratacao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), cargo, salario, dataContratacao);
    }

    @Override
    public String toString() {
        return "Funcionario{" +
                "codigo=" + getId() +  // Atualizado para getId
                ", nome='" + getNome() + '\'' +
                ", cpf='" + getCpf() + '\'' +
                ", cargo='" + cargo + '\'' +
                ", salario=" + salario +
                ", dataContratacao=" + dataContratacao +
                '}';
    }
}

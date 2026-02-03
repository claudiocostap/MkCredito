package com.makarios.mkcredito.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Embeddable
public class Endereco {

    @NotBlank
    @Size(max = 150)
    @Column(length = 150)
    private String logradouro;

    @NotBlank
    @Size(max = 20)
    @Column(length = 20)
    private String numero;

    @Size(max = 100)
    @Column(length = 100)
    private String complemento;

    @NotBlank
    @Size(max = 100)
    @Column(length = 100)
    private String bairro;

    @NotBlank
    @Pattern(regexp = "\\d{5}-\\d{3}", message = "CEP deve estar no formato 99999-999")
    @Column(length = 9)
    private String cep;

    @NotBlank
    @Size(max = 100)
    @Column(length = 100)
    private String cidade;

    @NotBlank
    @Size(min = 2, max = 2)
    @Column(length = 2)
    private String estado;

    // Getters e Setters
    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}

package com.makarios.mkcredito.model;

import java.util.Date;

public class Carne {
    private int numero;
    private String nomeCliente;
    private Date dataVencimento;
    private double valor;

    // Construtor
    public Carne(int numero, String nomeCliente, Date dataVencimento, double valor) {
        this.numero = numero;
        this.nomeCliente = nomeCliente;
        this.dataVencimento = dataVencimento;
        this.valor = valor;
    }

    // Métodos getters e setters
    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    // Método toString para exibir informações sobre o carnê
    @Override
    public String toString() {
        return "Carne{" +
                "numero=" + numero +
                ", nomeCliente='" + nomeCliente + '\'' +
                ", dataVencimento=" + dataVencimento +
                ", valor=" + valor +
                '}';
    }
}
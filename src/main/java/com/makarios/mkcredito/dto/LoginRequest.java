package com.makarios.mkcredito.dto;

import jakarta.validation.constraints.NotNull;

public class LoginRequest {

    @NotNull
    private String email;

    @NotNull
    private String senha;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}

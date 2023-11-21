package com.grupo5.AlquilerEquiposConstruccion.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginDTOResponse {

    @JsonProperty("jwt")
    private String jwt;

    @JsonProperty("role")
    private String role;

    public LoginDTOResponse(String jwt, String role) {
        this.jwt = jwt;
        this.role = role;
    }
}

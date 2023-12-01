package com.grupo5.AlquilerEquiposConstruccion.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginDTOResponse {

    @JsonProperty("jwt")
    private String jwt;

    @JsonProperty("role")
    private String role;

    @JsonProperty("name")
    private String name;

    @JsonProperty("lastname")
    private String lastName;

    @JsonProperty("email")
    private String email;

    public LoginDTOResponse(String jwt, String role, String name, String lastName, String email) {
        this.jwt = jwt;
        this.role = role;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
    }
}

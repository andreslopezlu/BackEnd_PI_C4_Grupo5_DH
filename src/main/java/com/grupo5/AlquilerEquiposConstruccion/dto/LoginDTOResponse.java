package com.grupo5.AlquilerEquiposConstruccion.dto;

public class LoginDTOResponse {

    private String jwt;
    private String rol;

    public LoginDTOResponse(String jwt, String rol) {
        this.jwt = jwt;
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "{" +
                "jwt:\"" + jwt + '\"' +
                ", rol:\"" + rol + '\"' +
                '}';
    }
}

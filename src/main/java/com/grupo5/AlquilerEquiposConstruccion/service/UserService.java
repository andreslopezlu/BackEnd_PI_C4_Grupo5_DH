package com.grupo5.AlquilerEquiposConstruccion.service;

import com.grupo5.AlquilerEquiposConstruccion.dto.UserDTO;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.BadRequestException;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.NotFoundException;
import com.grupo5.AlquilerEquiposConstruccion.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDTO> getAllUsers();
    Optional<UserDTO> getUserById(Integer id) throws NotFoundException;
    void saveUser(UserDTO userDTO) throws BadRequestException;
    UserDTO updateUser(UserDTO userDTO, Integer id) throws NotFoundException;
    void deleteUserById(Integer id) throws NotFoundException;
    }

package com.grupo5.AlquilerEquiposConstruccion.controller;

import com.grupo5.AlquilerEquiposConstruccion.dto.CategoryDTO;
import com.grupo5.AlquilerEquiposConstruccion.dto.UserDTO;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.BadRequestException;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.NotFoundException;
import com.grupo5.AlquilerEquiposConstruccion.model.User;
import com.grupo5.AlquilerEquiposConstruccion.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/by-email/{email}")
    public ResponseEntity<UserDTO> findByEmail(@PathVariable String email) throws NotFoundException {
        Optional<UserDTO> userSearch = userService.findByEmail(email);
        if(userSearch.isPresent()) {
            return ResponseEntity.ok(userSearch.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDTO, @PathVariable Integer id) throws Exception{
        Optional<UserDTO> userSearch = userService.getUserById(id);
        if(userSearch.isPresent()){
            return ResponseEntity.ok(userService.updateUser(userDTO, id));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with id: " + userDTO.getId() + " was not found.");
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> saveUser(@RequestBody UserDTO userDTO) throws BadRequestException {
        String encriptedPassword = passwordEncoder.encode(userDTO.getPassword());
        userDTO.setPassword(encriptedPassword);
        userService.saveUser(userDTO);
        return ResponseEntity.status(HttpStatus.OK).body("User with email: " + userDTO.getEmail() + " created successfully.");
    }
}

package com.grupo5.AlquilerEquiposConstruccion.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo5.AlquilerEquiposConstruccion.dto.RoleDTO;
import com.grupo5.AlquilerEquiposConstruccion.dto.UserDTO;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.BadRequestException;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.NotFoundException;
import com.grupo5.AlquilerEquiposConstruccion.model.Role;
import com.grupo5.AlquilerEquiposConstruccion.model.User;
import com.grupo5.AlquilerEquiposConstruccion.repository.UserRepository;
import com.grupo5.AlquilerEquiposConstruccion.service.EmailService;
import com.grupo5.AlquilerEquiposConstruccion.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.log4j.Logger;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final Logger logger = Logger.getLogger(CategoryServiceImpl.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    EmailService emailService;

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> usersDTO = new ArrayList<>();
        for (User user:
             users) {
            usersDTO.add(mapper.convertValue(user, UserDTO.class));
        }
        return usersDTO;
    }

    @Override
    public Optional<UserDTO> getUserById(Integer id) throws NotFoundException {
        User userFounded = userRepository.findById(id).orElseThrow(() -> new NotFoundException("The " +
                "user with the id: " + id + " was not found."));
        return Optional.ofNullable(mapper.convertValue(userFounded, UserDTO.class));
    }

    @Override
    public void saveUser(UserDTO userDTO) throws BadRequestException {
        if (userDTO.getName()==null || userDTO.getLastName()==null || userDTO.getEmail()==null || userDTO.getPhoneNumber()==null || userDTO.getPassword()==null){
            throw new BadRequestException("The user has null values.");
        }
        Boolean isExistingUser = userRepository.findByEmail(userDTO.getEmail()).isPresent();
        if (isExistingUser){
            throw new BadRequestException("The user already exists.");
        } else{
            User userCreated = mapper.convertValue(userDTO, User.class);
            logger.info("The user was created successfully.");
            mapper.convertValue(userRepository.save(userCreated), UserDTO.class);
        }

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(userDTO.getEmail());
        mailMessage.setSubject("Registro completo en AlquiConstruye!");
        mailMessage.setText("Bienvenido, hiciste el proceso de registro con el email: "
                + userDTO.getEmail()
                + ". "
                + "Para ingresar a tu cuenta haz click en el siguiente enlace: "
                + "https://www.google.com/. "
                + "Saludos desde G5 - C4 - DH");
        emailService.sendEmail(mailMessage);
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO, Integer id) throws NotFoundException{
        Optional<UserDTO> existingUser = getUserById(id);
        if (existingUser.isPresent()){
            existingUser.get().setRole(userDTO.getRole());
            User userUpdated = mapper.convertValue(existingUser, User.class);
            userRepository.save(userUpdated);
            logger.info("The user was updated successfully.");
        }
        return mapper.convertValue(existingUser, UserDTO.class);
    }

    @Override
    public void deleteUserById(Integer id) throws NotFoundException{
        Optional<UserDTO> userFounded = getUserById(id);
        userRepository.findById(id).orElseThrow(() -> new NotFoundException("The " +
                "user with the id: " + id + " was not found."));
        userRepository.deleteById(id);
    }

    @Override
    public RoleDTO getRoleByUsername(String username) throws NotFoundException {
        Optional<User> user = userRepository.findByEmail(username);
        if (user != null) {
            Role role = user.get().getRole();
            return mapper.convertValue(role, RoleDTO.class);
        }
        throw new NotFoundException("User not found.");
    }
}


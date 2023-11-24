package com.grupo5.AlquilerEquiposConstruccion.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo5.AlquilerEquiposConstruccion.dto.FavoriteDTO;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.BadRequestException;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.NotFoundException;
import com.grupo5.AlquilerEquiposConstruccion.model.Favorite;
import com.grupo5.AlquilerEquiposConstruccion.repository.FavoritesRepository;
import com.grupo5.AlquilerEquiposConstruccion.service.FavoriteService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    FavoritesRepository favoritesRepository;

    @Autowired
    private ObjectMapper mapper;

    private final Logger logger = Logger.getLogger(CityServiceImpl.class);

    @Override
    public List<FavoriteDTO> getAllFavorites() {
        List<Favorite> favorites = favoritesRepository.findAll();
        List<FavoriteDTO> favoritesDTO = new ArrayList<>();
        for(Favorite fav : favorites){
            favoritesDTO.add(mapper.convertValue(fav, FavoriteDTO.class));
        }
        return favoritesDTO;
    }

    @Override
    public Optional<FavoriteDTO> getFavoriteById(Integer id) throws NotFoundException {
        Favorite favoriteFounded = favoritesRepository.findById(id).orElseThrow(() -> new NotFoundException("The " +
                "favorite with the id: " + id + " was not found."));
        return Optional.ofNullable(mapper.convertValue(favoriteFounded, FavoriteDTO.class));
    }

    @Override
    public FavoriteDTO createFavorite(FavoriteDTO favorite) throws BadRequestException {
        if (favorite.getUser()==null || favorite.getProduct()==null){
            throw new BadRequestException("The favorite has null values.");
        } else{
            Favorite favoriteCreated = mapper.convertValue(favorite, Favorite.class);
            logger.info("The favorite was created successfully.");
            return mapper.convertValue(favoritesRepository.save(favoriteCreated), FavoriteDTO.class);
        }
    }

    @Override
    public FavoriteDTO updateFavorite(FavoriteDTO favorite) throws NotFoundException {
        Integer id = favorite.getId();
        Optional<FavoriteDTO> existingFavorite = getFavoriteById(id);
        if (existingFavorite.isPresent()){
            existingFavorite.get().setProduct(favorite.getProduct());
            existingFavorite.get().setUser(favorite.getUser());
            Favorite favoriteUpdated = mapper.convertValue(existingFavorite, Favorite.class);
            favoritesRepository.save(favoriteUpdated);
            logger.info("The favorite was updated successfully.");
        }
        return mapper.convertValue(existingFavorite, FavoriteDTO.class);
    }

    @Override
    public void deleteFavoriteById(Integer id) throws NotFoundException {
        favoritesRepository.findById(id).orElseThrow(() -> new NotFoundException("The " +
                "favorite with the id: " + id + " was not found."));
        favoritesRepository.deleteById(id);
    }

    @Override
    public Optional<FavoriteDTO> findByProduct_id(Integer id) throws NotFoundException {
        Favorite favoriteFounded = favoritesRepository.findByProduct_id(id).orElseThrow(() -> new NotFoundException("The " +
                "favorite for the product with the id: " + id + " was not found."));
        return Optional.ofNullable(mapper.convertValue(favoriteFounded, FavoriteDTO.class));
    }

    @Override
    public Optional<FavoriteDTO> findByUser_id(Integer id) throws NotFoundException {
        Favorite favoriteFounded = favoritesRepository.findByUser_id(id).orElseThrow(() -> new NotFoundException("The " +
                "favorite for the user with the id: " + id + " was not found."));
        return Optional.ofNullable(mapper.convertValue(favoriteFounded, FavoriteDTO.class));
    }
}

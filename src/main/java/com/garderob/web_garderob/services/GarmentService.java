package com.garderob.web_garderob.services;

import com.garderob.web_garderob.models.Account;
import com.garderob.web_garderob.models.FavoriteLook;
import com.garderob.web_garderob.models.Garment;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface GarmentService {

    void saveNewGarment(Garment newGarment, MultipartFile image, Account account) throws IOException;

    void deleteById(long id);

    Optional<Garment> findById(long id);

    void updateGarment(Garment newGarment, MultipartFile image, Account account) throws IOException;

    List<FavoriteLook> getGarmentsForEventId(long id);
    List<FavoriteLook> getFavoritesForUserId(long id);
    void saveAsFavorites (FavoriteLook favSet);

    void deleteFavoriteById(long id);
}

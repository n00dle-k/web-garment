package com.garderob.web_garderob.services;

import com.garderob.web_garderob.models.*;
import com.garderob.web_garderob.repo.EventsRepository;
import com.garderob.web_garderob.repo.FavoriteLookRepository;
import com.garderob.web_garderob.repo.GarmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GarmentServiceImpl implements GarmentService {

    private final GarmentRepository repository;
    private final FileStorageService fileStorageService;
    private final FavoriteLookRepository favoriteLookRepository;
    private final EventsRepository eventsRepository;

    @Autowired
    public GarmentServiceImpl(
            FileStorageService fileStorageService,
            GarmentRepository repository,
            FavoriteLookRepository favoriteLookRepository,
            EventsRepository eventsRepository) {
        this.repository = repository;
        this.fileStorageService = fileStorageService;
        this.favoriteLookRepository = favoriteLookRepository;
        this.eventsRepository = eventsRepository;
    }


    private void validateNewGarment(Garment newGarment, MultipartFile image) {
        if (image == null || image.isEmpty() || image.getOriginalFilename() == null || image.getOriginalFilename().isEmpty()) {
            throw new IllegalArgumentException("Image needs to be selected...");
        }
    }

    @Override
    public void saveNewGarment(Garment newGarment, MultipartFile image, Account account) throws IOException {
        validateNewGarment(newGarment, image);
        final var fileName = fileStorageService.uploadFile(image);
        newGarment.setImageFileName(fileName);
        for (Tag t : newGarment.getTags()) {
            newGarment.addTag(t);
        }
        newGarment.setAccount(account);
        repository.save(newGarment);
    }

    @Override
    public void deleteById(long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Garment> findById(long id) {
        return repository.findById(id);
    }

    @Override
    public void updateGarment(Garment newGarment, MultipartFile image, Account account) throws IOException {
        if (!image.isEmpty()) {
            final var fileName = fileStorageService.uploadFile(image);
            newGarment.setImageFileName(fileName);
        } else {
            final var oldState = repository.findById(newGarment.getId()).orElseThrow();
            newGarment.setImageFileName(oldState.getImageFileName());
        }
        newGarment.setAccount(account);
        repository.save(newGarment);
    }

    @Override
    public List<FavoriteLook> getGarmentsForEventId(long id) {
        final List<Garment> allEventGarments = repository.findAllGarmentsWithEventId(id).stream().filter(g -> !g.getIsInLaundry()).collect(Collectors.toCollection(ArrayList::new));
        final List<FavoriteLook> result = new ArrayList<>();
        final Event event = eventsRepository.findById(id).orElseThrow();
        if (!event.getIsTopLayer()) {
            allEventGarments.removeAll(allEventGarments.stream().filter(g -> g.getGarmentType().equals(GarmentType.TOP_2_LAYER)).toList());
        }
        while (result.size() < 3 && !allEventGarments.isEmpty()) {
            result.add(pickRandomGarmentLook(allEventGarments));
        }
        return result;
    }

    @Override
    public List<FavoriteLook> getFavoritesForUserId(long id) {
        return favoriteLookRepository.findAllByAccountId(id);
    }

    @Override
    public void saveAsFavorites(FavoriteLook favSet) {
        favoriteLookRepository.save(favSet);
    }

    @Override
    public void deleteFavoriteById(long id) {
        favoriteLookRepository.deleteById(id);
    }

    /**
     * Производит выборку образа. Изменяет входную коллекцию
     *
     * @param allEventGarments - все допустимые элементы одежды
     * @return
     */
    private FavoriteLook pickRandomGarmentLook(List<Garment> allEventGarments) {
        final FavoriteLook result = new FavoriteLook();
        final var dressOpt = allEventGarments.stream().filter(g -> g.getGarmentType().equals(GarmentType.DRESS)).findFirst();
        if (dressOpt.isPresent()) {
            final Garment dress = dressOpt.get();
            result.setDress(dress);
            allEventGarments.remove(dress);
        } else {
            final Optional<Garment> top1LayerOpt = allEventGarments.stream().filter(g -> g.getGarmentType().equals(GarmentType.TOP_1_LAYER)).findFirst();
            if (top1LayerOpt.isPresent()) {
                final Garment top1Layer = top1LayerOpt.get();
                allEventGarments.remove(top1Layer);
                result.setTop1Layer(top1Layer);
            }
        }
        final Optional<Garment> bottomOpt = allEventGarments.stream().filter(g -> g.getGarmentType().equals(GarmentType.BOTTOM)).findFirst();
        if (bottomOpt.isPresent()) {
            final Garment bottom = bottomOpt.get();
            allEventGarments.remove(bottom);
            result.setBottom(bottom);
        }
        final Optional<Garment> shoesOpt = allEventGarments.stream().filter(g -> g.getGarmentType().equals(GarmentType.SHOES)).findFirst();
        if (shoesOpt.isPresent()) {
            final Garment shoes = shoesOpt.get();
            allEventGarments.remove(shoes);
            result.setShoes(shoes);
        }
        final Optional<Garment> top2LayerOpt = allEventGarments.stream().filter(g -> g.getGarmentType().equals(GarmentType.TOP_2_LAYER)).findFirst();
        if (top2LayerOpt.isPresent()) {
            final Garment top2Layer = top2LayerOpt.get();
            allEventGarments.remove(top2Layer);
            result.setTop2Layer(top2Layer);
        }
        return result;
    }
}

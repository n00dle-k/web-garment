package com.garderob.web_garderob.repo;

import com.garderob.web_garderob.models.FavoriteLook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteLookRepository extends JpaRepository<FavoriteLook, Long> {
    List<FavoriteLook> findAllByAccountId(long id);
}

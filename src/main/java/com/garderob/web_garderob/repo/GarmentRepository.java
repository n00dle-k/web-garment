package com.garderob.web_garderob.repo;

import com.garderob.web_garderob.models.Garment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GarmentRepository extends CrudRepository<Garment, Long> {
    @Query(value = "SELECT g FROM Garment g JOIN g.tags t JOIN t.events e WHERE e.id = :PARAM")
    List<Garment> findAllGarmentsWithEventId(@Param("PARAM") long id);
}

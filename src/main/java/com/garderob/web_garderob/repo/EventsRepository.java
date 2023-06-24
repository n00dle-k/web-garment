package com.garderob.web_garderob.repo;

import com.garderob.web_garderob.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventsRepository extends JpaRepository<Event, Long> {
}

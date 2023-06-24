package com.garderob.web_garderob.services;

import com.garderob.web_garderob.models.Account;
import com.garderob.web_garderob.models.Event;

import java.util.Optional;

public interface EventService {
    void saveNewEvent(Event newEvent, Account account);

    Optional<Event> findById(long id);

    void updateEvent(Event updatedEventState, Account account);

    void deleteById(long id);
}

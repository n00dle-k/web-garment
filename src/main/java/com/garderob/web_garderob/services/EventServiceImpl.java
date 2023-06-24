package com.garderob.web_garderob.services;

import com.garderob.web_garderob.models.Account;
import com.garderob.web_garderob.models.Event;
import com.garderob.web_garderob.models.Tag;
import com.garderob.web_garderob.repo.EventsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {

    private final EventsRepository eventsRepository;

    @Autowired
    public EventServiceImpl(EventsRepository eventsRepository) {
        this.eventsRepository = eventsRepository;
    }

    @Override
    public void saveNewEvent(Event newEvent, Account account) {
        for (Tag t : newEvent.getTags()) {
            newEvent.addTag(t);
        }
        newEvent.setAccount(account);
        eventsRepository.save(newEvent);
    }

    @Override
    public Optional<Event> findById(long id) {
        return eventsRepository.findById(id);
    }

    @Override
    public void updateEvent(Event updatedEventState, Account account) {
        updatedEventState.setAccount(account);
        eventsRepository.save(updatedEventState);
    }

    @Override
    public void deleteById(long id) {
        eventsRepository.deleteById(id);
    }
}

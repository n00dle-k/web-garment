package com.garderob.web_garderob.services;

import com.garderob.web_garderob.models.Tag;
import com.garderob.web_garderob.repo.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository repository;

    @Autowired
    public TagServiceImpl(TagRepository repository) {
        this.repository = repository;
    }

    @Override
    public void saveNewTag(Tag newTag) {
        repository.save(newTag);
    }

    @Override
    public Optional<Tag> findById(long id) {
        return repository.findById(id);
    }

    @Override
    public void updateTagName(long id, String name) {
        final var tagToUpdate = repository.findById(id).orElseThrow();
        tagToUpdate.setName(name);
        repository.save(tagToUpdate);
    }

    @Override
    public void deleteById(long id) {
        repository.deleteById(id);
    }
}

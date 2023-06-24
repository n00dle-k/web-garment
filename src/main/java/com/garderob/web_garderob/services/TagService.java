package com.garderob.web_garderob.services;

import com.garderob.web_garderob.models.Tag;

import java.util.Optional;

public interface TagService {
    void saveNewTag(Tag newTag);

    Optional<Tag> findById(long id);

    void updateTagName(long id, String name);

    void deleteById(long id);
}

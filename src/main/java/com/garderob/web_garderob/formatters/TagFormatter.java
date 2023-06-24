package com.garderob.web_garderob.formatters;

import com.garderob.web_garderob.models.Tag;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

public class TagFormatter implements Formatter<Tag> {
    @Override
    public Tag parse(String text, Locale locale) throws ParseException {
        final var t = new Tag();
        t.setId(Long.parseLong(text));
        return t;
    }

    @Override
    public String print(Tag object, Locale locale) {
        return object.getId().toString();
    }
}

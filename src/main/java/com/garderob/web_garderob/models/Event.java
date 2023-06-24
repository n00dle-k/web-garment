package com.garderob.web_garderob.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JsonIgnore
    private Account account;

    @ManyToMany
    @JoinTable(name = "events_tags", joinColumns = @JoinColumn(name = "events_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
    @JsonIgnore
    private Set<Tag> tags = new HashSet<>();

    @Column
    private String name;

    @Column
    private boolean isTopLayer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public void addTag(Tag t) {
        this.tags.add(t);
        t.getEvents().add(this);
    }

    public boolean getIsTopLayer() {
        return isTopLayer;
    }

    public void setIsTopLayer(boolean topLayer) {
        isTopLayer = topLayer;
    }
}

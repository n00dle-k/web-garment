package com.garderob.web_garderob.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "garments_tags",
            inverseJoinColumns = @JoinColumn(name = "garment_id"),
            joinColumns = @JoinColumn(name = "tag_id")
    )
    @JsonIgnore
    private final Set<Garment> garments = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "events_tags",
            inverseJoinColumns = @JoinColumn(name = "events_id"),
            joinColumns = @JoinColumn(name = "tag_id")
    )
    @JsonIgnore
    private final Set<Event> events = new HashSet<>();

    @ManyToOne
    @JsonIgnore
    private Account account;

    @Column
    private String name;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Garment> getGarments() {
        return garments;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Tag other = (Tag) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}

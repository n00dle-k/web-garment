package com.garderob.web_garderob.models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    private String login;

    @Column
    private String password;

    @OneToMany(mappedBy = "account")
    private final Set<Tag> tags = new HashSet<>();

    @OneToMany(mappedBy = "account")
    private final Set<Garment> garments = new HashSet<>();

    @OneToMany(mappedBy = "account")
    private final Set<Event> events = new HashSet<>();

    @Column
    private String imageFileName;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public Set<Garment> getGarments() {
        return garments;
    }

    public Set<Event> getEvents() {
        return events;
    }
}

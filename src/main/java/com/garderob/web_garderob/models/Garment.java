package com.garderob.web_garderob.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "garments")
public class Garment {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String title;
    @Column
    private String description;
    @ManyToMany
    @JoinTable(
            name = "garments_tags",
            joinColumns = @JoinColumn(name = "garment_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

    @Column
    private String imageFileName;

    @ManyToOne
    @JsonIgnore
    private Account account;

    @Column
    @Enumerated(EnumType.STRING)
    private GarmentType garmentType;

    @Column
    private boolean isInLaundry;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public Set<Tag> getTags() {
        return tags;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void addTag(Tag t) {
        this.tags.add(t);
        t.getGarments().add(this);
    }

    public GarmentType getGarmentType() {
        return garmentType;
    }

    public void setGarmentType(GarmentType garmentType) {
        this.garmentType = garmentType;
    }

    public boolean getIsInLaundry() {
        return isInLaundry;
    }

    public void setIsInLaundry(boolean inLaundry) {
        isInLaundry = inLaundry;
    }
}

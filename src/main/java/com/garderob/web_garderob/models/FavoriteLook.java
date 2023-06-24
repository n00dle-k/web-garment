package com.garderob.web_garderob.models;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Entity
public class FavoriteLook {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Account account;

    @OneToOne
    private Garment bottom;

    @OneToOne
    private Garment top1Layer;

    @OneToOne
    private Garment top2Layer;

    @OneToOne
    private Garment dress;

    @OneToOne
    private Garment shoes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Garment getBottom() {
        return bottom;
    }

    public void setBottom(Garment bottom) {
        this.bottom = bottom;
    }

    public Garment getTop1Layer() {
        return top1Layer;
    }

    public void setTop1Layer(Garment top1Layer) {
        this.top1Layer = top1Layer;
    }

    public Garment getTop2Layer() {
        return top2Layer;
    }

    public void setTop2Layer(Garment top2Layer) {
        this.top2Layer = top2Layer;
    }

    public Garment getDress() {
        return dress;
    }

    public void setDress(Garment dress) {
        this.dress = dress;
    }

    public Garment getShoes() {
        return shoes;
    }

    public void setShoes(Garment shoes) {
        this.shoes = shoes;
    }

    public List<Garment> getAllGarments() {
        return Stream.of(bottom, top1Layer, top2Layer, dress, shoes).filter(Objects::nonNull).toList();
    }
}

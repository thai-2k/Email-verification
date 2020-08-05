package com.example.springboottodomvc.model;

import javax.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private Boolean isActive;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private  VericationToken vericationToken;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public VericationToken getVericationToken() {
        return vericationToken;
    }

    public void setVericationToken(VericationToken vericationToken) {
        this.vericationToken = vericationToken;
    }
}

package com.temelyan.pomoapp.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "USERS")
public class User extends AbstractEntity {

    @Email
    @NotBlank
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private
    List<Pomo> pomos;

    public User() {
    }

    public User(Integer id, @Email @NotBlank String email) {
        this.id = id;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Pomo> getPomos() {
        return pomos;
    }

    public void setPomos(List<Pomo> pomos) {
        this.pomos = pomos;
    }
}

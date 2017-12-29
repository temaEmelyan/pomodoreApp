package com.temelyan.pomoapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "USERS")
public class User extends AbstractEntity {

    @Email
    @NotBlank
    @Column(name = "email")
    private
    String email;

    @OneToMany
    private
    List<Pomo> pomos;

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

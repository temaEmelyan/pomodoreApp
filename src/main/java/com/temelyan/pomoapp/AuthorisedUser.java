package com.temelyan.pomoapp;

import com.temelyan.pomoapp.model.AbstractEntity;

public class AuthorisedUser {

    public static int getId() {
        return AbstractEntity.START_SEQ;
    }
}

package com.store.cashback.enums;

import lombok.Getter;

@Getter
public enum Categories {

    POP("pop", "Pop"),
    MPB("mpb", "Mpb"),
    CLASSICAL("classical", "classical"),
    ROCK("rock", "rock");

    private String id;

    private String label;

    Categories(String id, String label){
        this.label = label;
        this.id = id;
    }


}

package com.delivery.dto;

import java.util.ArrayList;
import java.util.List;

public class TextsDTO {
    private List<Long> texts = new ArrayList<>();

    public TextsDTO(List<Long> texts) {
        this.texts = texts;
    }

    public List<Long> getTexts() {
        return texts;
    }

    public void setTexts(List<Long> texts) {
        this.texts = texts;
    }

}

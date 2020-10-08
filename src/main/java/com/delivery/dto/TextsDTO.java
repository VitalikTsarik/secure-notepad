package com.delivery.dto;

import java.util.ArrayList;
import java.util.List;

public class TextsDTO {
    public TextsDTO(List<TextDTO> texts) {
        this.texts = texts;
    }

    public List<TextDTO> getTexts() {
        return texts;
    }

    public void setTexts(List<TextDTO> texts) {
        this.texts = texts;
    }

    private List<TextDTO> texts = new ArrayList<TextDTO>();

    public static class TextDTO {
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        private String name;
        private long id;
    }
}

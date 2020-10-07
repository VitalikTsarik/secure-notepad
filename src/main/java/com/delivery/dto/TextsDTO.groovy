package com.delivery.dto

class TextsDTO {
    static class TextDTO {
        String name
        long id
    }
    List<TextDTO> texts = new ArrayList<>()

    TextsDTO(List<TextDTO> texts) {
        this.texts = texts
    }
}

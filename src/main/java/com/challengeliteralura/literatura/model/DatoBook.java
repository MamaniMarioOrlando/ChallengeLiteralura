package com.challengeliteralura.literatura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatoBook(
        //la notacion @JsonAlias solo sirve para lectura los datos dela api
        //la notacion @Jsonproperyti sirve para lectura y escritura
        @JsonAlias("title") String titulo,
        @JsonAlias("download_count") Integer download_count,
        @JsonAlias("authors") List<DatoPerson> authors,
        @JsonAlias("languages")List<String> languages
) {
    public String getFirstLanguage() {
        return languages != null && !languages.isEmpty() ? languages.get(0) : null;
    }
}
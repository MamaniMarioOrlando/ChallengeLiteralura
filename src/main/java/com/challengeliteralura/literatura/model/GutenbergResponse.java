package com.challengeliteralura.literatura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GutenbergResponse {
    private List<DatoBook> results;
    public List<DatoBook> getResults() {
        return results;
    }

    public void setResults(List<DatoBook> results) {
        this.results = results;
    }
}

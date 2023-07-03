package com.ianwordfrequencyrest.ianwordfrequencyrest;

import com.ianwordfrequencyrest.ianwordfrequencyrest.core.WordFrequencyAnalyzer;
import com.ianwordfrequencyrest.ianwordfrequencyrest.model.WordFrequency;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/word-frequency")
public class WordFrequencyResource {

    private final WordFrequencyAnalyzer analyzer;

    @Inject
    public WordFrequencyResource(WordFrequencyAnalyzer analyzer) {
        this.analyzer = analyzer;
    }

    @GET
    @Path("/highest-frequency")
    @Produces(MediaType.APPLICATION_JSON)
    public int getHighestFrequency(@QueryParam("text") String text) {
        return analyzer.calculateHighestFrequency(text);
    }

    @GET
    @Path("/frequency-for-word")
    @Produces(MediaType.APPLICATION_JSON)
    public int getFrequencyForWord(
            @QueryParam("text") String text,
            @QueryParam("word") String word
    ) {
        return analyzer.calculateFrequencyForWord(text, word);
    }

    @GET
    @Path("/most-frequent-words")
    @Produces(MediaType.APPLICATION_JSON)
    public List<WordFrequency> getMostFrequentWords(
            @QueryParam("text") String text,
            @QueryParam("n") int n
    ) {
        return analyzer.calculateMostFrequentNWords(text, n);
    }
}
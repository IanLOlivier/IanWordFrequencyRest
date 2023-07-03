package com.ianwordfrequencyrest.ianwordfrequencyrest.core.impl;

import com.ianwordfrequencyrest.ianwordfrequencyrest.core.WordFrequencyAnalyzer;
import com.ianwordfrequencyrest.ianwordfrequencyrest.model.WordFrequency;
import com.ianwordfrequencyrest.ianwordfrequencyrest.model.WordFrequencyImpl;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.*;

@ApplicationScoped
public class WordFrequencyAnalyzerImpl implements WordFrequencyAnalyzer {
    @Override
    public int calculateHighestFrequency(String text) {
        List<WordFrequency> wordFrequencies = calculateWordFrequencies(text);
        if (wordFrequencies.isEmpty()) {
            return 0;
        }
        return Collections.max(wordFrequencies, Comparator.comparing(WordFrequency::getFrequency)).getFrequency();
    }

    @Override
    public int calculateFrequencyForWord(String text, String word) {
        List<WordFrequency> wordFrequencies = calculateWordFrequencies(text);

        final Optional<WordFrequency> result = wordFrequencies.stream()
                .filter(wordFrequency -> wordFrequency.getWord().equalsIgnoreCase(word))
                .findFirst();

        if (result.isPresent())
        {
            return result.get().getFrequency();
        }
        return 0;
    }

    @Override
    public List<WordFrequency> calculateMostFrequentNWords(String text, int n) {
        List<WordFrequency> wordFrequencies = calculateWordFrequencies(text);

        // sort by frequency then by alphabetical order
        wordFrequencies.sort((wf1, wf2) -> {
            if (wf1.getFrequency() != wf2.getFrequency()) {
                return wf2.getFrequency() - wf1.getFrequency();
            } else {
                return wf1.getWord().compareToIgnoreCase(wf2.getWord());
            }
        });

        // return the sublist if list is bigger than n
        if (wordFrequencies.size() > n) {
            wordFrequencies = wordFrequencies.subList(0, n);
        }

        return wordFrequencies;
    }

    private List<WordFrequency> calculateWordFrequencies(String text) {
        Map<String, Integer> wordFrequencyMap = new HashMap<>();
        /*Since input text in case insensitive we can take it to lower case and words can be seperated by various
         * seperator characters we can split by \\W+
         */
        String[] words = text.toLowerCase().split("\\W+");

        for (String word : words) {
            wordFrequencyMap.put(word, wordFrequencyMap.getOrDefault(word, 0) + 1);
        }

        List<WordFrequency> wordFrequencies = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : wordFrequencyMap.entrySet()) {
            wordFrequencies.add(new WordFrequencyImpl(entry.getKey(), entry.getValue()));
        }

        return wordFrequencies;
    }
}

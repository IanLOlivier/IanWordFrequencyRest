package com.ianwordfrequencyrest.ianwordfrequencyrest.core.impl;

import com.ianwordfrequencyrest.ianwordfrequencyrest.model.WordFrequency;
import com.ianwordfrequencyrest.ianwordfrequencyrest.model.WordFrequencyImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class WordFrequencyImplTest {

    @Spy
    @InjectMocks
    private WordFrequencyAnalyzerImpl wordFrequencyAnalyzer;


    @Test
    public void calculateHighestFrequency_Should_ReturnHighestFrequency_ForProvidedInput() {

        final String inputText = "The, the sun rises";
        final int expectedResult = 2;


        final int actualResult = wordFrequencyAnalyzer.calculateHighestFrequency(inputText);
        assertEquals(expectedResult, actualResult);
        assertNotEquals(3, actualResult);
    }

    @Test
    public void calculateFrequencyForWord_Should_ReturnFrequency_ForProvidedWordAndInput() {

        final String inputWord = "the";
        final String inputText = "The, the sun rises";
        final int expectedResult = 2;


        final int actualResult = wordFrequencyAnalyzer.calculateFrequencyForWord(inputText, inputWord);
        assertEquals(expectedResult, actualResult);
        assertNotEquals(3, actualResult);
    }

    @Test
    public void calculateFrequencyForNWords_Should_ReturnFrequency_ForProvidedWordAndInput() {

        final int inputNumber = 3;
        final String inputText = "the,the sun sun sun rises over the lake";
        final List<WordFrequency> expectedResult = Arrays.asList(
                WordFrequencyImpl.builder()
                        .word("sun")
                        .frequency(3)
                        .build(),
                WordFrequencyImpl.builder()
                        .word("the")
                        .frequency(3)
                        .build(),
                WordFrequencyImpl.builder()
                        .word("lake")
                        .frequency(1)
                        .build());


        final List<WordFrequency> actualResult = wordFrequencyAnalyzer.calculateMostFrequentNWords(inputText, inputNumber);

        assertEquals(actualResult.size(), expectedResult.size());
        assertFalse(actualResult.isEmpty());
        assertNotEquals(4, actualResult.size());

        assertEquals(actualResult.get(0).getFrequency(), expectedResult.get(0).getFrequency());
        assertEquals(actualResult.get(0).getWord(),expectedResult.get(0).getWord());
        assertEquals(actualResult.get(1).getFrequency(), expectedResult.get(1).getFrequency());
        assertEquals(actualResult.get(1).getWord(),expectedResult.get(1).getWord());
    }
}

package com.ianwordfrequencyrest.ianwordfrequencyrest;

import com.ianwordfrequencyrest.ianwordfrequencyrest.core.WordFrequencyAnalyzer;
import com.ianwordfrequencyrest.ianwordfrequencyrest.model.WordFrequency;
import com.ianwordfrequencyrest.ianwordfrequencyrest.model.WordFrequencyImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WordFrequencyResourceTest {

    @Mock
    private WordFrequencyAnalyzer analyzer;

    @InjectMocks
    private WordFrequencyResource resource;

    @BeforeEach
    public void setup() {
        resource = new WordFrequencyResource(analyzer);
    }

    @Test
    public void GetHighestFrequency_Should_ReturnHighestFrequency_ForProvidedInput_WhenCalledWithResource() {

        String inputText = "The the sun rises";
        int expectedResult = 2;
        when(analyzer.calculateHighestFrequency(inputText)).thenReturn(expectedResult);
        final int response = resource.getHighestFrequency(inputText);

        assertEquals(expectedResult, response);
    }

    @Test
    public void calculateFrequencyForWord_Should_ReturnHighestFrequency_ForProvidedWordInInput_WhenCalledWithResource() {

        final String inputWord = "the";
        final String inputText = "The, the sun rises";
        final int expectedResult = 2;
        when(analyzer.calculateFrequencyForWord(inputText,inputWord)).thenReturn(expectedResult);
        final int response = resource.getFrequencyForWord(inputText,inputWord);

        assertEquals(expectedResult, response);
    }

    @Test
    public void calculateFrequencyForNWords_Should_ReturnHighestFrequency_ForProvidedWordInInput_WhenCalledWithResource() {

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
        when(analyzer.calculateMostFrequentNWords(inputText,inputNumber)).thenReturn(expectedResult);
        final List<WordFrequency> response = resource.getMostFrequentWords(inputText,inputNumber);

        assertEquals(response.size(), expectedResult.size());
    }
}

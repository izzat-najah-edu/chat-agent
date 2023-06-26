package com.izzatalsharif.openai.chatagent.dto;

import java.util.List;

/**
 * A simple data object to receive responses from the test agent.
 *
 * @param colors maps to "colors" array in the JSON.
 * @param places maps to "places" array in the JSON.
 */
public record ColorsAndPlaces(
        List<String> colors,
        List<String> places
) {
}
package com.polbohub.service;

import com.polbohub.model.Competition;
import com.polbohub.model.InscriptionEntry;
import com.polbohub.model.User;

import java.util.List;

public interface InscriptionAssistantService {
    
    /**
     * Generates a list of recommended runs for a user in a specific competition.
     * Recommendations are based on team points maximization.
     *
     * @param user The user to generate recommendations for.
     * @param competition The target competition.
     * @return List of recommended entries with explanations.
     */
    List<InscriptionRecommendation> recommendRuns(User user, Competition competition);

    record InscriptionRecommendation(
        InscriptionEntry entry,
        String explanation,
        double projectedPoints
    ) {}
}

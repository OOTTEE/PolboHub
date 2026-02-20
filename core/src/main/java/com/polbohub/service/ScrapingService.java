package com.polbohub.service;

import com.polbohub.model.Competition;

import java.util.List;

public interface ScrapingService {
    /**
     * Triggers a scrape of official results from the federation website.
     * @param competitionUrl Optional specific URL to scrape, otherwise scrapes index.
     */
    void scrapeOfficialResults(String competitionUrl);

    /**
     * Returns a list of competitions found during scraping that are not yet in our DB.
     */
    List<Competition> findNewCompetitions();
}

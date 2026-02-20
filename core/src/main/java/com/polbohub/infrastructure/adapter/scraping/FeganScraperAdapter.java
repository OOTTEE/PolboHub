package com.polbohub.infrastructure.adapter.scraping;

import com.polbohub.model.Competition;
import com.polbohub.service.ScrapingService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Service
public class FeganScraperAdapter implements ScrapingService {

    private static final String FEGAN_URL = "https://www.fegan.org";

    @Override
    public void scrapeOfficialResults(String competitionUrl) {
        String url = competitionUrl != null ? competitionUrl : FEGAN_URL;
        try {
            Document doc = Jsoup.connect(url).get();
            // Parsing logic would go here:
            // 1. Find table rows
            // 2. Extract data
            // 3. Map to Domain objects
            // 4. Save via Port/Repository
            System.out.println("Scraped title: " + doc.title());
        } catch (IOException e) {
            throw new RuntimeException("Failed to scrape Fegan: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Competition> findNewCompetitions() {
        // Implementation to find new competitions
        return Collections.emptyList();
    }
}

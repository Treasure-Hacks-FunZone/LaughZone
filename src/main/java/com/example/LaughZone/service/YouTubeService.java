package com.example.LaughZone.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchResult;

import org.springframework.stereotype.Service;

@Service
public class YouTubeService {

    private static final String APPLICATION_NAME = "My YouTube API Client";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String API_KEY = "";
    private static final long MAX_RESULTS = 10;

    public List<SearchResult> search(String query) throws IOException, GeneralSecurityException {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        final YouTube youtube = new YouTube.Builder(httpTransport, JSON_FACTORY, getRequestInitializer())
                .setApplicationName(APPLICATION_NAME)
                .build();
        final YouTube.Search.List search = youtube.search().list("id,snippet");
        search.setKey(API_KEY);
        search.setQ(query);
        search.setType("video");
        search.setMaxResults(MAX_RESULTS);
        search.setFields("items(id(videoId),snippet(publishedAt,title,description,thumbnails(default)))");
        final com.google.api.services.youtube.model.SearchListResponse searchResponse = search.execute();
        final List<SearchResult> searchResults = searchResponse.getItems();
        if (searchResults == null) {
            return new ArrayList<>();
        }
        return searchResults;
    }

    private static HttpRequestInitializer getRequestInitializer() {
        return httpRequest -> httpRequest.setConnectTimeout(10000).setReadTimeout(10000);
    }
}

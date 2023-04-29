package service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.stereotype.Service;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;


@Service
public class YouTubeService {
    private static final String APPLICATION_NAME = "Youtube Searcher";
    private static final String API_KEY = "AIzaSyA-UQmq59YTU8_sSJD0AdCwpA4MZdjzeM0";

    private final YouTube youtube;

    public YouTubeService() throws GeneralSecurityException, IOException {
        this.youtube = new YouTube.Builder(GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(), null)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public List<SearchResult> search(String query) throws IOException {
        YouTube.Search.List search = youtube.search().list("id,snippet");
        search.setKey(API_KEY);
        search.setQ(query);
        search.setType("video");

        SearchListResponse response = search.execute();
        return response.getItems();
    }
}

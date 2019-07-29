package com.store.cashback.spotify;

import com.store.cashback.enums.Genres;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.model_objects.specification.Recommendations;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Slf4j
@Component
public class SpotifyClient {

    @Autowired
    private Environment env;


    public Recommendations getRecommendations(Genres genre) throws IOException, SpotifyWebApiException {
        return this.spotifyApi().getRecommendations().limit(50).seed_genres(
                genre.getId()).build().execute();
    }

    public Track getTrack(String id) throws IOException, SpotifyWebApiException {
        return this.spotifyApi().getTrack(id).build().execute();
    }

    private SpotifyApi spotifyApi(){
        SpotifyApi spotifyApi = null;
        try {
            spotifyApi = new SpotifyApi.Builder()
                    .setClientId(env.getProperty("SPOTIFY_CLIENT", this.env.getProperty("spotify.client")))
                    .setClientSecret(env.getProperty("SPOTIFY_CLIENT_SECRET",this.env.getProperty("spotify.client.secret")))
                    .build();
            ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials()
                    .build();
            final ClientCredentials clientCredentials = clientCredentialsRequest.execute();
            spotifyApi.setAccessToken(clientCredentials.getAccessToken());
            return spotifyApi;
        } catch (IOException | SpotifyWebApiException e) {
            log.error("Error: {}", e.getMessage());
        }

        return spotifyApi;
    }


}

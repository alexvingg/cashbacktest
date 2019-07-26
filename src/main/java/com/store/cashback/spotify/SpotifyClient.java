package com.store.cashback.spotify;

import com.store.cashback.enums.Genres;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.model_objects.specification.Recommendations;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class SpotifyClient {

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId("7e9419289c984d7fba856f2e1718bcbd")
            .setClientSecret("5f5c823f44b246af896c2728e7db9da0")
            .build();

    private static final ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials()
            .build();

    public Recommendations getRecommendations(Genres genre) throws IOException, SpotifyWebApiException {
        this.updateAcessToken();
        return this.spotifyApi.getRecommendations().limit(50).seed_genres(
                genre.getId()).build().execute();
    }

    public Track getTrack(String id) throws IOException, SpotifyWebApiException {
        return this.spotifyApi.getTrack(id).build().execute();
    }

    private void updateAcessToken(){
        try {
            final ClientCredentials clientCredentials = clientCredentialsRequest.execute();
            this.spotifyApi.setAccessToken(clientCredentials.getAccessToken());
            log.info("Expires in: {}", clientCredentials.getExpiresIn());
        } catch (IOException | SpotifyWebApiException e) {
            log.error("Error: {}", e.getMessage());
        }
    }


}

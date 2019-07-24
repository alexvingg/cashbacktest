package com.store.cashback.service;

import com.store.cashback.enums.Categories;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.model_objects.specification.*;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
@Slf4j
public class SpotifyService {

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId("7e9419289c984d7fba856f2e1718bcbd")
            .setClientSecret("5f5c823f44b246af896c2728e7db9da0")
            .build();

    private static final ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials()
            .build();

    public Map<String, Map<String, String>> getDiscs() throws Exception {
        this.updateAcessToken();
        log.info("getCatalog");
        Map<String, Map<String, String>> map = new HashMap<>();
        Arrays.asList(Categories.values()).forEach(categorie -> {
            try{
                map.put(categorie.getId(), new HashMap<>());
                List<PlaylistSimplified> playlistSimplifieds = Arrays.asList(spotifyApi.getCategorysPlaylists(
                        categorie.getId()).build().execute().getItems());
                playlistSimplifieds.stream().forEach(playlistSimplified -> {
                    try {
                        List<PlaylistTrack> playlistTracks = Arrays.asList(spotifyApi.getPlaylistsTracks(playlistSimplified.getId()).build().execute().getItems());
                        playlistTracks.parallelStream().limit(50).forEach(playlistTrack -> {
                            if(map.get(categorie.getId()).size() < 50) {
                                map.get(categorie.getId()).put(playlistTrack.getTrack().getAlbum().getName(),
                                        playlistTrack.getTrack().getAlbum().getName());
                            }
                        });
                    }catch (Exception e){
                        log.error(e.getMessage());
                    }
                });
            }catch (Exception ex){
                log.error(ex.getMessage());
            }
        });
        return map;
    }

    private void updateAcessToken(){
        try {
            final ClientCredentials clientCredentials = clientCredentialsRequest.execute();
            spotifyApi.setAccessToken(clientCredentials.getAccessToken());
            log.info("Expires in: {}", clientCredentials.getExpiresIn());
        } catch (IOException | SpotifyWebApiException e) {
            log.error("Error: {}", e.getMessage());
        }
    }

}

package com.store.cashback.service;

import com.store.cashback.entity.Album;
import com.store.cashback.enums.Categories;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.model_objects.specification.*;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SpotifyService {

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId("7e9419289c984d7fba856f2e1718bcbd")
            .setClientSecret("5f5c823f44b246af896c2728e7db9da0")
            .build();

    private static final ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials()
            .build();

    @Autowired
    private AlbumService albumService;

    public Map<String, List<Album>> getAlbums() {
        this.updateAcessToken();
        log.info("getAlbums");
        Map<String, List<Album>> map = new HashMap<>();
        Arrays.asList(Categories.values()).forEach(category -> {
            try{
                map.put(category.getId(), new ArrayList<>());
                Recommendations recommendations = spotifyApi.getRecommendations().limit(50).seed_genres(
                        category.getId()).build().execute();
                map.put(category.getId(),  this.mountAlbum(category.getId(), recommendations, map.get(category.getId())));
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

    private List<Album> mountAlbum(String category, Recommendations recommendations, List<Album> values){
        Arrays.asList(recommendations.getTracks()).parallelStream().map(TrackSimplified::getId).distinct().limit(50).collect(
                Collectors.toList()).stream().forEach(id -> {
            try {
                Track track = spotifyApi.getTrack(id).build().execute();

                Album album = new Album(null,
                        String.valueOf(UUID.randomUUID()).replace("-", ""),
                        track.getAlbum().getName(),
                        category);

                values.add(album);

            }catch (Exception e){
                log.error(e.getMessage());
            }
        });

        return values;
    }

    @EventListener
    public void seedAlbumSpotify(ContextRefreshedEvent event) {
        log.info("seedAlbumSpotify");
        List<Album> albums = this.albumService.findAll();

        if(albums.isEmpty()){
            this.getAlbums().forEach((s, albums1) -> {
                this.albumService.saveAlbums(albums1);
            });
        }
    }

}

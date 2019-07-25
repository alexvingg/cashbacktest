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

    public Map<String, List<String>> getAlbums() {
        this.updateAcessToken();
        log.info("getCatalog");
        Map<String, List<String>> map = new HashMap<>();
        Arrays.asList(Categories.values()).forEach(categorie -> {
            try{
                map.put(categorie.getId(), new ArrayList<>());
                Recommendations recommendations = spotifyApi.getRecommendations().limit(50).seed_genres(
                        categorie.getId()).build().execute();
                map.put(categorie.getId(),  this.mountAlbum(recommendations, 0, map.get(categorie.getId())));
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

    private List<String> mountAlbum(Recommendations recommendations, int skip, List<String> values){
        Arrays.asList(recommendations.getTracks()).parallelStream().map(TrackSimplified::getId).distinct().limit(50).collect(
                Collectors.toList()).parallelStream().forEach(id -> {
            try {
                Track track = spotifyApi.getTrack(id).build().execute();
                values.add(track.getAlbum().getName());

            }catch (Exception e){
                log.error(e.getMessage());
            }
        });

//        Arrays.asList(recommendations.getTracks()).stream().skip(skip).limit(skip + 1).forEach(trackSimplified -> {
//            try {
//                Track track = spotifyApi.getTrack(trackSimplified.getId()).build().execute();
//                track.getAlbum().getName();
//                values.add(track.getAlbum().getName());
//                returnValues.addAll(values.stream().distinct().collect(
//                        Collectors.toList()).stream().limit(50).collect(Collectors.toList()));
//                if(returnValues.size() != 50){
//                    returnValues2.addAll(mountAlbum(recommendations, skip + 1, returnValues));
//                }else{
//                    returnValues2.addAll(returnValues);
//                }
//            }catch (Exception e){
//                log.error(e.getMessage());
//            }
//        });

        return values;
    }

}

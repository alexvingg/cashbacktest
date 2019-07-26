package com.store.cashback.service;

import com.store.cashback.entity.Album;
import com.store.cashback.enums.Genres;
import com.store.cashback.spotify.SpotifyClient;
import com.store.cashback.util.UUIDUtils;
import com.wrapper.spotify.model_objects.specification.Recommendations;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.model_objects.specification.TrackSimplified;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SpotifyService {

    @Autowired
    private AlbumService albumService;

    @Autowired
    private SpotifyClient spotifyClient;

    public Map<String, List<Album>> getAlbums() {
        log.info("getAlbums");
        Map<String, List<Album>> map = new HashMap<>();
        Arrays.asList(Genres.values()).forEach(genre -> {
            try{
                map.put(genre.getId(), new ArrayList<>());
                Recommendations recommendations = this.spotifyClient.getRecommendations(genre);
                map.put(genre.getId(),  this.mountAlbum(genre.getId(), recommendations, map.get(genre.getId())));
            }catch (Exception ex){
                log.error(ex.getMessage());
            }
        });

        return map;
    }

    private List<Album> mountAlbum(String genre, Recommendations recommendations, List<Album> values){
        Arrays.asList(recommendations.getTracks()).parallelStream().map(TrackSimplified::getId).distinct().limit(50).collect(
                Collectors.toList()).stream().forEach(id -> {
            try {
                Track track = this.spotifyClient.getTrack(id);

                Album album = new Album(null,
                        UUIDUtils.cleanUuid(),
                        track.getAlbum().getName(),
                        genre,
                        new BigDecimal(BigInteger.valueOf(new Random().nextInt(100001)), 2));

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

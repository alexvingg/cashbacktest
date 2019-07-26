package com.store.cashback;

import com.store.cashback.entity.Album;
import com.store.cashback.enums.Genres;
import com.store.cashback.service.SpotifyService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpotifyServiceTest {

    @Autowired
    private SpotifyService spotifyService;

    private static Map<String, List<Album>> albums;

    private static boolean started = false;

    private static final int SIZE = 50;

    @Before
    public void init() throws Exception {
        if(!started){
            albums = spotifyService.getAlbums();
            started = true;
        }
    }

    @Test
    public void validateCatalogRock() {
        assertEquals(albums.get(Genres.ROCK.getId()).size(), SIZE);
    }

    @Test
    public void validateCatalogPop() {
        assertEquals(albums.get(Genres.POP.getId()).size(), SIZE);
    }

    @Test
    public void validateCatalogClassic() {
        assertEquals(albums.get(Genres.CLASSICAL.getId()).size(), SIZE);
    }

    @Test
    public void validateCatalogMpb() {
        assertEquals(albums.get(Genres.MPB.getId()).size(), SIZE);
    }

}

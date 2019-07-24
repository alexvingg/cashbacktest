package com.store.cashback;

import com.store.cashback.enums.Categories;
import com.store.cashback.service.SpotifyService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpotifyServiceTest {

    @Autowired
    private SpotifyService spotifyService;

    private static Map<String, Map<String, String>> albums;

    private static boolean started = false;

    private static final int SIZE = 50;

    @Before
    public void init() throws Exception{
        if(!started){
            albums = spotifyService.getDiscs();
            started = true;
        }
    }

    @Test
    public void validateCatalogRock() {
        assertEquals(albums.get(Categories.ROCK.getId()).size(), SIZE);
    }

    @Test
    public void validateCatalogPop() {
        assertEquals(albums.get(Categories.POP.getId()).size(), SIZE);
    }

    @Test
    public void validateCatalogClassic() {
        assertEquals(albums.get(Categories.CLASSIC.getId()).size(), SIZE);
    }

    @Test
    public void validateCatalogMpb() {
        assertEquals(albums.get(Categories.MPB.getId()).size(), SIZE);
    }

}

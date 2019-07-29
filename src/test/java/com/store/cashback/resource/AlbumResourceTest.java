package com.store.cashback.resource;

import com.store.cashback.entity.Album;
import com.store.cashback.enums.Genres;
import com.store.cashback.service.AlbumService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AlbumResource.class)
public class AlbumResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlbumService albumService;

    private Page<Album> albumPage;

    private Album album;

    @Before
    public void AlbumResourceTest(){
        album = new Album(1L, "AA", "Teste", "rock", new BigDecimal("10"));
        List<Album> albums = Arrays.asList(album);
        albumPage = new PageImpl(albums);
    }

    @Test
    public void when_return_status_code_200() throws Exception {
        given(albumService.findAlbums(Genres.ROCK.getId(), PageRequest.of(0, 10))).willReturn(albumPage);
        this.mockMvc.perform(get("/v1/album?genre={genre}", "rock"))
                .andExpect(status().isOk());
    }

    @Test
    public void when_return_status_code_400() throws Exception {
        given(albumService.findAlbums(Genres.ROCK.getId(), PageRequest.of(0, 10))).willReturn(albumPage);
        this.mockMvc.perform(get("/v1/album"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void when_slug_is_valid_status_code_200() throws Exception{
        given(albumService.findBySlug(album.getSlug())).willReturn(album);
        this.mockMvc.perform(get("/v1/album/{slug}", album.getSlug()))
                .andExpect(status().isOk());
    }
}
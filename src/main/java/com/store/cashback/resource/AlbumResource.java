package com.store.cashback.resource;

import com.store.cashback.entity.Album;
import com.store.cashback.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("album")
public class AlbumResource {

    @Autowired
    private AlbumService albumService;

    @GetMapping
    ResponseEntity<Page<Album>> findAlbums(@RequestParam String genre, Pageable pageable) {

        return ResponseEntity.ok().body(albumService.findAlbums(genre, pageable));
    }

    @GetMapping(path = "/{slug}")
    ResponseEntity<Album> findBySlug(@PathVariable String slug) {
        return ResponseEntity.ok().body(albumService.findBySlug(slug));
    }

}

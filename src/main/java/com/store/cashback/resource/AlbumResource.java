package com.store.cashback.resource;

import com.store.cashback.exception.InvalidParamsException;
import com.store.cashback.response.Response;
import com.store.cashback.service.AlbumService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("v1/album")
@Slf4j
public class AlbumResource {

    @Autowired
    private AlbumService albumService;

    @GetMapping
    ResponseEntity<Response> findAlbums(@RequestParam String genre, Pageable pageable) {

        log.info("findAlbums {}", genre);
        try{
            return ResponseEntity.ok().body(Response.ok().addData("albums", albumService.findAlbums(genre, pageable)));
        }catch (InvalidParamsException ex){
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response.ok(ex.getMessage()));
        }catch (Exception ex){
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Response.internalServerError());
        }
    }

    @GetMapping(path = "/{slug}")
    ResponseEntity<Response> findBySlug(@PathVariable String slug) {

        log.info("findBySlug {}", slug);
        try{
            return ResponseEntity.ok().body(Response.ok().addData("album", albumService.findBySlug(slug)));
        }catch (InvalidParamsException ex){
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response.ok(ex.getMessage()));
        }catch (Exception ex){
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Response.internalServerError());
        }
    }

}

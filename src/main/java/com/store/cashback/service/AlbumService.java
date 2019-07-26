package com.store.cashback.service;

import com.store.cashback.entity.Album;
import com.store.cashback.repository.AlbumRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Slf4j
public class AlbumService {

    @Autowired
    private AlbumRepository albumRepository;

    public void saveAlbums(List<Album> albums){
        log.info("save albums length {}", albums.size());
        this.albumRepository.saveAll(albums);
    }

    public Page<Album> findAlbums(String genre, Pageable pageable){
        if(!StringUtils.hasText(genre)){
            throw new RuntimeException("invalid params");
        }

        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), 10, Sort.by("name").ascending());
        return this.albumRepository.findByGenre(genre, pageRequest);
    }

    public List<Album> findAll(){
        log.info("find all");
        return this.albumRepository.findAll();
    }

    public Album findBySlug(String slug){
        log.info("findBySlug {}", slug);
        if(!StringUtils.hasText(slug)){
            throw new RuntimeException("invalid params");
        }

        return this.albumRepository.findBySlug(slug);
    }
}

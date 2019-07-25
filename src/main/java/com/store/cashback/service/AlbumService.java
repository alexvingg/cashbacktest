package com.store.cashback.service;

import com.store.cashback.enity.Album;
import com.store.cashback.repository.AlbumRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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

    public List<Album> findAlbums(String genre, Integer page){
        if(!StringUtils.hasText(genre)){
            throw new RuntimeException("invalid params");
        }

        if(page == null){
            page = 0;
        }

        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by("name").ascending());
        return this.albumRepository.findByGenre(genre, pageRequest);
    }
}

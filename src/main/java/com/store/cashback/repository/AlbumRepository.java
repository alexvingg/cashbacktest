package com.store.cashback.repository;

import com.store.cashback.enity.Album;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, Long> {

    List<Album> findByGenre(String genre, Pageable pageable);

}

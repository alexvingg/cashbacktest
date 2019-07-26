package com.store.cashback.repository;

import com.store.cashback.entity.Album;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, Long> {

    Page<Album> findByGenre(String genre, Pageable pageable);

    Album findBySlug(String slug);
}

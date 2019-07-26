package com.store.cashback.repository;

import com.store.cashback.entity.Album;
import com.store.cashback.entity.Cashback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.util.List;

public interface CashbackRepository extends JpaRepository<Cashback, Long> {

    Cashback findByDayOfWeekAndGenre(DayOfWeek dayOfWeek, String genre);
}

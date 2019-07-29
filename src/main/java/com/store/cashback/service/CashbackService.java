package com.store.cashback.service;

import com.store.cashback.entity.Album;
import com.store.cashback.entity.Cashback;
import com.store.cashback.repository.AlbumRepository;
import com.store.cashback.repository.CashbackRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.DayOfWeek;
import java.util.List;

@Service
@Slf4j
public class CashbackService {

    @Autowired
    private CashbackRepository cashbackRepository;

    public Cashback findCashbackDayOfWeekAndGenre(String genre, DayOfWeek dayOfWeek){
        log.info("findCashbackDayOfWeekAndGenre {} {}", dayOfWeek.getValue(), genre);
        return this.cashbackRepository.findByDayOfWeekAndGenre(dayOfWeek.getValue(), genre);
    }
}

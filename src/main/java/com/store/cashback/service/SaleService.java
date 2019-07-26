package com.store.cashback.service;

import com.store.cashback.entity.Album;
import com.store.cashback.entity.Cashback;
import com.store.cashback.entity.Sale;
import com.store.cashback.entity.SaleAlbum;
import com.store.cashback.exception.InvalidParamsException;
import com.store.cashback.repository.SaleRepository;
import com.store.cashback.util.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private AlbumService albumService;

    @Autowired
    private CashbackService cashbackService;

    public Page<Sale> findAllSales(Date dateStart, Date dateEnd, Pageable pageable){
        log.info("findAllSales {} {} ", dateStart, dateEnd);
        if(dateStart == null || dateEnd == null){
            throw new InvalidParamsException("invalid params");
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateEnd);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);

        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), 10, Sort.by("createdAt").descending());
        return this.saleRepository.findByCreatedAtBetween(dateStart, calendar.getTime(), pageRequest);
    }

    public Sale findSale(String slug){
        log.info("findSale {}", slug);
        if(!StringUtils.hasText(slug)){
            throw new InvalidParamsException("invalid params");
        }
        return this.saleRepository.findBySlug(slug);
    }

    public Sale sell(List<Album> albums){

        log.info("sell albums {}", albums);

        Sale newSale = new Sale();
        newSale.setSlug(UUIDUtils.cleanUuid());
        newSale.setSaleAlbums(new ArrayList<>());
        DayOfWeek today = LocalDate.now().getDayOfWeek();

        albums.forEach(album1 -> {
            Album album = this.albumService.findBySlug(album1.getSlug());

            if(album == null){
                throw new InvalidParamsException("invalid params");
            }

            Cashback cashback = this.cashbackService.findCashbackDayOfWeekAndGenre(album.getGenre(), today);
            BigDecimal cashbackValue = album.getPrice().multiply(BigDecimal.valueOf(
                    cashback.getPercent()).divide(BigDecimal.valueOf(100)));
            SaleAlbum newSaleAlbum = new SaleAlbum();
            newSaleAlbum.setAlbumName(album.getName());
            newSaleAlbum.setAlbumSlug(album.getSlug());
            newSaleAlbum.setAlbumPrice(album.getPrice());
            newSaleAlbum.setCashback(cashback.getPercent());
            newSaleAlbum.setSalePrice(album.getPrice().subtract(cashbackValue));
            newSaleAlbum.setSale(newSale);
            newSale.getSaleAlbums().add(newSaleAlbum);
        });

        return this.saleRepository.save(newSale);
    }


}

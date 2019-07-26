package com.store.cashback.service;

import com.store.cashback.entity.Album;
import com.store.cashback.entity.Sale;
import com.store.cashback.repository.SaleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

@Service
@Slf4j
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;

    public Page<Sale> findAllSales(Date dateStart, Date dateEnd, Pageable pageable){
        if(dateStart == null || dateEnd == null){
            throw new RuntimeException("invalid params");
        }
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), 10, Sort.by("createdAt").descending());
        return this.saleRepository.findByCreatedAtBetween(dateStart, dateEnd, pageRequest);
    }

    public Sale findSale(String slug){
        if(!StringUtils.hasText(slug)){
            throw new RuntimeException("invalid params");
        }
        return this.saleRepository.findBySlug(slug);
    }


}

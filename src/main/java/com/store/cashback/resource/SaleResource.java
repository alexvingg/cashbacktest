package com.store.cashback.resource;

import com.store.cashback.entity.Sale;
import com.store.cashback.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@RestController
@RequestMapping("sale")
public class SaleResource {

    @Autowired
    private SaleService saleService;

    @GetMapping
    ResponseEntity<Page<Sale>> findSales(@RequestParam("dateStart") @DateTimeFormat(pattern="dd/MM/yyyy") Date dateStart,
                                         @RequestParam("dateEnd") @DateTimeFormat(pattern="dd/MM/yyyy") Date dateEnd,
                                         Pageable pageable) {
        return ResponseEntity.ok().body(saleService.findAllSales(dateStart, dateEnd, pageable));
    }

    @GetMapping("/{slug}")
    ResponseEntity<Sale> findSales(@PathVariable String slug) {
        return ResponseEntity.ok().body(saleService.findSale(slug));
    }

}

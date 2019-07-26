package com.store.cashback.resource;

import com.store.cashback.entity.Album;
import com.store.cashback.exception.InvalidParamsException;
import com.store.cashback.response.Response;
import com.store.cashback.service.SaleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("v1/sale")
@Slf4j
public class SaleResource {

    @Autowired
    private SaleService saleService;

    @GetMapping
    ResponseEntity<Response> findSales(@RequestParam("dateStart") @DateTimeFormat(pattern="dd/MM/yyyy") Date dateStart,
                                         @RequestParam("dateEnd") @DateTimeFormat(pattern="dd/MM/yyyy") Date dateEnd,
                                         Pageable pageable) {
        log.info("findSales {} {}", dateStart, dateEnd);

        try{
            return ResponseEntity.ok().body(Response.ok().addData("sales",
                    this.saleService.findAllSales(dateStart, dateEnd, pageable)));
        }catch (InvalidParamsException ex){
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response.ok(ex.getMessage()));
        }catch (Exception ex){
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Response.internalServerError());
        }

    }

    @GetMapping("/{slug}")
    ResponseEntity<Response> findSale(@PathVariable String slug) {
        log.info("findSales {}", slug);
        try{
            return ResponseEntity.ok().body(Response.ok().addData("sale", saleService.findSale(slug)));
        }catch (InvalidParamsException ex){
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response.ok(ex.getMessage()));
        }catch (Exception ex){
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Response.internalServerError());
        }
    }


    @PostMapping
    ResponseEntity<Response> saveSale(@RequestBody List<Album> albums) {
        log.info("saveSale {}", albums);
        try{
            return ResponseEntity.ok().body(Response.ok().addData("sale",
                    this.saleService.sell(albums)));
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

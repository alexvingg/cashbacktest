package com.store.cashback.resource;

import com.store.cashback.entity.Sale;
import com.store.cashback.service.SaleService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.time.LocalDate;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(SaleResource.class)
public class SaleResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SaleService saleService;

    private Page<Sale> salePage;

    private Sale sale;

    @Before
    public void SaleResourceTest() {
        sale = new Sale();
        sale.setSlug("AA");
    }

    @Test
    public void when_sales_period_valid_status_code_200() throws Exception {
        LocalDate localDate = LocalDate.now();

        given(saleService.findAllSales(Date.valueOf(localDate.minusDays(2)),
                Date.valueOf(localDate), PageRequest.of(0, 10))).willReturn(salePage);

        this.mockMvc.perform(get("/v1/sale?dateStart={dateStart}&dateEnd={dateEnd}",
                "01/01/2019", "01/08/2019")).andExpect(status().isOk());
    }

    @Test
    public void when_sales_invalid_period_valid_status_code_400() throws Exception{
        LocalDate localDate = LocalDate.now();

        given(saleService.findAllSales(Date.valueOf(localDate.minusDays(2)),
                Date.valueOf(localDate), PageRequest.of(0, 10))).willReturn(salePage);

        this.mockMvc.perform(get("/v1/sale")).andExpect(status().isBadRequest());
    }

    @Test
    public void when_sale_valid_slug_code_200() throws Exception {
        given(saleService.findSale(sale.getSlug())).willReturn(sale);
        this.mockMvc.perform(get("/v1/sale/{slug}", sale.getSlug())).andExpect(status().isOk());
    }

    @Test
    public void when_sale_valid_slug_code_400() throws Exception {
        given(saleService.findSale("")).willReturn(sale);
        this.mockMvc.perform(get("/v1/sale/")).andExpect(status().isBadRequest());
    }
}
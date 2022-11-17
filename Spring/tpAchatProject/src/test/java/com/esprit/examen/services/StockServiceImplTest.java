package com.esprit.examen.services;

import com.esprit.examen.entities.Stock;
import com.esprit.examen.entities.dto.StockDTO;
import com.esprit.examen.repositories.StockRepository;
import com.esprit.examen.services.impl.StockServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@Slf4j
@ExtendWith(MockitoExtension.class)
public class StockServiceImplTest {
	@Mock
	private StockRepository stockRepository;
	@InjectMocks
	private StockServiceImpl stockService;
	private Stock s1;
	private Stock s2;

	private String finalMessage;
	ModelMapper modelMapper;
	@BeforeEach
	public void init() {
		this.s1 = new Stock();
		this.s1.setIdStock(0L);
		this.s1.setLibelleStock("Test 1");
		this.s1.setQte(10);
		this.s1.setQteMin(11);

		this.s2 = new Stock();
		this.s2.setIdStock(1L);
		this.s2.setLibelleStock("Test 2");
		this.s2.setQte(22);
		this.s2.setQteMin(22);

		this.finalMessage = "";
		this.modelMapper = new ModelMapper();
	}
	@Test
	@DisplayName("Test Add to Stock")
	public void testAddStock() {
		log.info("entry function : testAddStock");
		init();
		when(stockRepository.save(any(Stock.class))).thenReturn(s1);
		StockDTO sdto = modelMapper.map(s1, StockDTO.class);
		Stock snew = stockService.addStock(sdto);
		assertNotNull(snew);
		assertThat(snew.getLibelleStock()).isEqualTo("Test 1");
		log.info("exit function : testAddStock");
	}

	@Test
	@DisplayName("Test Retrieve from stock")
	public void testRetrieveStock() {
		log.info("entry function : testRetrieveStock");
		init();
		List<Stock> list = new ArrayList<>();
		list.add(s1);
		list.add(s2);
		when(stockRepository.findAll()).thenReturn(list);
		List<Stock> Stocks = stockService.retrieveAllStocks();
		assertEquals(2, Stocks.size());
		assertNotNull(Stocks);
		log.info("exit function : testRetrieveStock");
	}
	@Test
	@DisplayName("Test Get Stock by id")
	public void testGetStockById() {
		log.info("entry function : testGetStockById");
		init();
		when(stockRepository.save(any(Stock.class))).thenReturn(s1);
		StockDTO srm=modelMapper.map(s1, StockDTO.class);
		Stock snew=stockService.addStock(srm);
		when(stockRepository.findById(anyLong())).thenReturn(Optional.of(s1));
		Stock existingStock = stockService.retrieveStock(snew.getIdStock());
		assertNotNull(existingStock);
		assertThat(existingStock.getIdStock()).isNotNull();
		log.info("exit function : testGetStockById");
	}
	@Test
	@DisplayName("Test Delete from stock")
	public void testDeleteStock() {
		log.info("entry function : testDeleteStock");
		init();
		Long StockId =1L;
		when(stockRepository.findById(anyLong())).thenReturn(Optional.of(s1));
		doNothing().when(stockRepository).deleteById(anyLong());
		stockService.deleteStock(StockId);
		verify(stockRepository, times(1)).deleteById(anyLong());
		log.info("exit function : testDeleteStock");
	}

	@Test
	@DisplayName("Test Update Stock")
	public void testUpdateStock() {
		log.info("entry function : testUpdateStock");
		init();
		when(stockRepository.findById(anyLong())).thenReturn(Optional.of(s1));
		when(stockRepository.save(any(Stock.class))).thenReturn(s1);
		s1.setLibelleStock("faza");
		StockDTO sdto=modelMapper.map(s1, StockDTO.class);
		Stock exisitingStock = stockService.updateStock(sdto);
		assertNotNull(exisitingStock);
		assertEquals("faza", exisitingStock.getLibelleStock());
		log.info("exit function : testUpdateStock");
	}

	@Test
	@DisplayName("Test Retrieve Status Stock")
	public void testRetrieveStatusStock() {
		log.info("entry function : testRetrieveStatusStock");
		init();
		List<Stock> stocksEnRouge = new ArrayList<>();
		stocksEnRouge.add(s1);
		when(stockRepository.retrieveStatusStock()).thenReturn(stocksEnRouge);
		assertThat(stockService.retrieveStatusStock()).contains("le stock Test 1 a une quantité de 10 inférieur à la quantité minimale a ne pas dépasser de 11");
		log.info("exit function : testRetrieveStatusStock");
	}
}

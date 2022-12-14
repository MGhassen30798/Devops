package com.esprit.examen.services;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.esprit.examen.services.impl.ProduitServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit4.SpringRunner;

import com.esprit.examen.entities.Produit;
import com.esprit.examen.entities.dto.ProduitDTO;
import com.esprit.examen.repositories.ProduitRepository;


@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {
	@Mock
	private ProduitRepository produitRepository;

	@InjectMocks
	private ProduitServiceImpl produitService;

	private Produit p1;
	private Produit p2;
	ModelMapper modelMapper;

	@BeforeEach
	public void init() {
		this.p1 = new Produit();
		this.p1.setIdProduit(0L);
		this.p1.setPrix(100);
		this.p1.setLibelleProduit("Avatar");
		this.p2 = new Produit();
		this.p2.setIdProduit(1L);
		this.p2.setPrix(100);
		this.p2.setLibelleProduit("Avatar");
		this.modelMapper = new ModelMapper();
	}

	@Test
	public void testAddProduit() {
		init();
		when(produitRepository.save(any(Produit.class))).thenReturn(p1);
		ProduitDTO prm=modelMapper.map(p1, ProduitDTO.class);
		Produit pnew=produitService.addProduit(prm);
		assertNotNull(pnew);
		assertThat(pnew.getPrix()).isEqualTo(100);
	}
	@Test
	public void save() {
		init();
		when(produitRepository.save(any(Produit.class))).thenReturn(p1);
		ProduitDTO prm=modelMapper.map(p1, ProduitDTO.class);
		Produit newProduit = produitService.addProduit(prm);
		assertNotNull(newProduit);
		assertThat(newProduit.getPrix()).isEqualTo(100);
	}
	
	@Test
	public void getProduits() {
		init();
		List<Produit> list = new ArrayList<>();
		list.add(p1);
		list.add(p2);
		when(produitRepository.findAll()).thenReturn(list);
		List<Produit> Produits = produitService.retrieveAllProduits();
		assertEquals(2, Produits.size());
		assertNotNull(Produits);
	}
	
	@Test
	public void getProduitById() {
		init();
		when(produitRepository.save(any(Produit.class))).thenReturn(p1);
		ProduitDTO prm=modelMapper.map(p1, ProduitDTO.class);
		Produit pnew=produitService.addProduit(prm);
		when(produitRepository.findById(anyLong())).thenReturn(Optional.of(p1));
		Produit existingProduit = produitService.retrieveProduit(pnew.getIdProduit());
		assertNotNull(existingProduit);
		assertThat(existingProduit.getIdProduit()).isNotNull();
	}
	
	@Test
	public void updateProduit() {
		init();
		when(produitRepository.findById(anyLong())).thenReturn(Optional.of(p1));
		
		when(produitRepository.save(any(Produit.class))).thenReturn(p1);
		p1.setLibelleProduit("Fantacy");
		ProduitDTO prm=modelMapper.map(p1, ProduitDTO.class);
		Produit exisitingProduit = produitService.updateProduit(prm);
		
		assertNotNull(exisitingProduit);
		assertEquals("Fantacy", exisitingProduit.getLibelleProduit());
	}
	
	@Test
	public void deleteProduit() {
		init();
		Long ProduitId = 1L;
		when(produitRepository.findById(anyLong())).thenReturn(Optional.of(p1));
		doNothing().when(produitRepository).deleteById(anyLong());
		produitService.deleteProduit(ProduitId);
		verify(produitRepository, times(1)).deleteById(anyLong());
		
	}
}

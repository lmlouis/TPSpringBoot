package com.gpao.demo;

import com.gpao.demo.dao.ProduitRepository;
import com.gpao.demo.entities.Produit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
class ProduitsApplicationTests {

	@Autowired
	private ProduitRepository produitRepository;

	@Test
	public void testCreateProduit(){
		Produit prod = new Produit("PC Dell", 2200.500, new Date());
		produitRepository.save(prod);
	}

	@Test
	public void testUpdateProduit(){
		Produit p = produitRepository.findById(1l).get();
		System.out.println(p);
	}

	@Test
	public void testDeleteProduit(){
		produitRepository.deleteById(1l);
	}



	@Test
	public void testListerTousProduits(){
		List<Produit> prods = produitRepository.findAll();
		for(Produit p : prods){
			System.out.println(p);
		}
	}
	@Test
	void contextLoads() {
	}

}

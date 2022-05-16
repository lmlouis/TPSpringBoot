package com.gpao.demo.RestController;

import com.gpao.demo.entities.Produit;
import com.gpao.demo.service.ProduitServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProduitController {
    @Autowired
    ProduitServiceImpl produitServiceImpl;

    @GetMapping("/produits")
    private List<Produit> getAllProduits(){
        return produitServiceImpl.getAllProduits();
    }

    @PostMapping("/addProduit")
     private Long saveProduit(@RequestBody Produit produit)
    {
        produitServiceImpl.saveProduit(produit);
        return produit.getIdProduit();
    }

    @GetMapping("/produit/{produitId}")
    private Produit getProduit(@PathVariable("produitId") long Id)
    {
        return produitServiceImpl.getProduit(Id);
    }

    @DeleteMapping("/produit/{produitId}")
    private void produitDelete(@PathVariable("produitId") long Id){
        produitServiceImpl.deleteProduitById(Id);
    }

    @PutMapping("/produits")
    private Produit update(@RequestBody Produit produit)
    {
        produitServiceImpl.updateProduit(produit);
        return produit;
    }
}

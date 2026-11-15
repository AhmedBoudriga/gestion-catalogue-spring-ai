package com.example.gestioncatalogue.controller;

import com.example.gestioncatalogue.entities.Produit;
import com.example.gestioncatalogue.service.IserviceProduit;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class ProduitController {
    private IserviceProduit serviceProduit;

    @GetMapping("/all")
    public List<Produit> getAll(){
        return serviceProduit.getAllProduits();
    }
    @GetMapping("/{id}")
    public Produit getProduit (@PathVariable Long id){
        return serviceProduit.getProduit(id);

    }
    @GetMapping("/search")
    public List<Produit> getProductsBKW (@RequestParam String mc){
        return serviceProduit.getProduitsParMC(mc);
    }
    @DeleteMapping("/[id")
    public void deleteProduct(@PathVariable Long id){
        serviceProduit.supprimerProduit(id);
    }
    @PostMapping("/add")
    public void addProduct(@RequestBody Produit p ){
        serviceProduit.ajouterProduit(p);
    }
    @PutMapping("/update/{id}")
        public void updateProduct(@PathVariable Long id,@RequestBody Produit p){
        serviceProduit.modifierProduit(id,p);
        }

}

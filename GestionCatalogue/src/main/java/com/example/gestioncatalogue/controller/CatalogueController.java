package com.example.gestioncatalogue.controller;

import com.example.gestioncatalogue.entities.Categorie;
import com.example.gestioncatalogue.entities.Produit;
import com.example.gestioncatalogue.service.IserviceCatalogue;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/categories")
public class CatalogueController {
    private IserviceCatalogue serviceCatalogue;

    @GetMapping("/all")
    public List<Categorie> getAll() {
        return serviceCatalogue.getAllCategories();
    }

    @GetMapping("/{id}")
    public Categorie getCategorie(@PathVariable Long id) {
        return serviceCatalogue.getCategorie(id);
    }

    @GetMapping("/{id}/produits")
    public List<Produit> getProduitsParCategorie(@PathVariable Long id) {
        return serviceCatalogue.getProduitsParCategorie(id);
    }

    @PostMapping("/add")
    public void addCategorie(@RequestBody Categorie c) {
        serviceCatalogue.ajouterCategorie(c);
    }

    @PutMapping("/update/{id}")
    public void updateCategorie(@PathVariable Long id, @RequestBody Categorie c) {
        serviceCatalogue.modifierCategorie(id, c);
    }

    @DeleteMapping("/{id}")
    public void deleteCategorie(@PathVariable Long id) {
        serviceCatalogue.supprimerCategorie(id);
    }
}
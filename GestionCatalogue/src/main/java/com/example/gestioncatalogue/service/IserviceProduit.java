package com.example.gestioncatalogue.service;

import com.example.gestioncatalogue.entities.Produit;

import java.util.List;

public interface IserviceProduit {
    void ajouterProduit(Produit p);
    void supprimerProduit(Long id);
    void modifierProduit(Long id,Produit p);
    Produit getProduit(Long id);
    List<Produit> getAllProduits();
    List<Produit> getProduitsParMC(String mc);
}

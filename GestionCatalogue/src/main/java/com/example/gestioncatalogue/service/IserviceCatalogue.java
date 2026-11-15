package com.example.gestioncatalogue.service;

import com.example.gestioncatalogue.entities.Categorie;
import com.example.gestioncatalogue.entities.Produit;

import java.util.List;

public interface IserviceCatalogue {
    void ajouterCategorie(Categorie c);
    void supprimerCategorie(Long id);
    void modifierCategorie(Long id, Categorie c);
    Categorie getCategorie(Long id);
    List<Categorie> getAllCategories();
    List<Produit> getProduitsParCategorie(Long id);
}
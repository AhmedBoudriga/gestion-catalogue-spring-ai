package com.example.gestioncatalogue.service;

import com.example.gestioncatalogue.entities.Categorie;
import com.example.gestioncatalogue.entities.Produit;
import com.example.gestioncatalogue.repository.CategorieRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class serviceCatalogue implements IserviceCatalogue {
    private CategorieRepository categorieRepository;

    @Override
    public void ajouterCategorie(Categorie c) {
        categorieRepository.save(c);
    }

    @Override
    public void supprimerCategorie(Long id) {
        Categorie c = categorieRepository.findById(id).orElse(null);
        if (c != null) {
            for (Produit p : c.getProduits()) {
                p.setCategorie(null);
            }
            categorieRepository.delete(c);
        }
    }

    @Override
    public void modifierCategorie(Long id, Categorie c) {
        Categorie cc = categorieRepository.findById(id).orElse(null);
        if (cc != null) {
            cc.setNom(c.getNom());
            categorieRepository.save(cc);
        }
    }

    @Override
    public Categorie getCategorie(Long id) {
        return categorieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("l'Id: " + id + " de la catégorie n'existe pas"));
    }

    @Override
    public List<Categorie> getAllCategories() {
        return categorieRepository.findAll();
    }

    @Override
    public List<Produit> getProduitsParCategorie(Long id) {
        return getCategorie(id).getProduits();
    }
}
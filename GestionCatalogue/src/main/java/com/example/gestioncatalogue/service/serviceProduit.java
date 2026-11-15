package com.example.gestioncatalogue.service;

import com.example.gestioncatalogue.entities.Produit;
import com.example.gestioncatalogue.repository.ProduitRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@AllArgsConstructor
@Service
public class serviceProduit implements IserviceProduit{
    private ProduitRepository produitRepository;
    @Override
    public void ajouterProduit(Produit p) {
        produitRepository.save(p);

    }

    @Override
    public void supprimerProduit(Long id) {
        produitRepository.deleteById(id);

    }

    @Override
    public void modifierProduit(Long id, Produit p) {
        Produit pp=produitRepository.findById(id).orElse(null);
        if(pp!=null){
            pp.setNom(p.getNom());
            pp.setPrix(p.getPrix());
            pp.setQuantite(p.getQuantite());
            produitRepository.save(pp);
        }
    }

    @Override
    public Produit getProduit(Long id) {
        return produitRepository.findById(id).orElseThrow(()->new RuntimeException("l'Id: "+id+"du produit n existe pas"));

    }

    @Override
    public List<Produit> getAllProduits() {
        return produitRepository.findAll();
    }

    @Override
    public List<Produit> getProduitsParMC(String mc) {
        return List.of();
    }
}

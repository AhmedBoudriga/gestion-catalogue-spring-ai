package com.example.gestioncatalogue.tools;

import com.example.gestioncatalogue.entities.Categorie;
import com.example.gestioncatalogue.entities.Produit;
import com.example.gestioncatalogue.repository.CategorieRepository;
import com.example.gestioncatalogue.repository.ProduitRepository;
import lombok.AllArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class ProductTools {

    private ProduitRepository produitRepository;
    private CategorieRepository categorieRepository;

    @Tool(name = "getProducts", description = "Retourne la liste de tous les produits dans la base de donnees")
    public List<Produit> getAllProducts() {
        return produitRepository.findAll();
    }

    @Tool(name = "getProduct", description = "Retourne un produit a partir de son id")
    public Produit getProduit(@ToolParam(description = "L'id du produit", required = true) Long id) {
        return produitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Le produit avec l'id " + id + " n'existe pas"));
    }

    // ===================== DEFI 1 =====================
    @Tool(name = "getCatalogueStatistics",
            description = "Fournit des statistiques cles sur le catalogue : total, prix moyen, produit le plus cher et le moins cher")
    public Map<String, Object> getCatalogueStatistics() {
        List<Produit> produits = produitRepository.findAll();
        Map<String, Object> stats = new LinkedHashMap<>();

        stats.put("totalArticles", produits.size());

        if (produits.isEmpty()) {
            stats.put("message", "Le catalogue est vide.");
            return stats;
        }

        double moyenne = produits.stream()
                .mapToDouble(Produit::getPrix)
                .average()
                .orElse(0.0);
        stats.put("prixMoyen", Math.round(moyenne * 100.0) / 100.0);

        Produit plusCher = produits.stream()
                .max(Comparator.comparingDouble(Produit::getPrix))
                .get();
        Produit moinsCher = produits.stream()
                .min(Comparator.comparingDouble(Produit::getPrix))
                .get();

        stats.put("produitPlusCher", Map.of("nom", plusCher.getNom(), "prix", plusCher.getPrix()));
        stats.put("produitMoinsCher", Map.of("nom", moinsCher.getNom(), "prix", moinsCher.getPrix()));

        return stats;
    }

    // ===================== DEFI 2 =====================
    @Tool(name = "createProduct",
            description = "Cree et enregistre un nouveau produit dans la base de donnees, optionnellement rattache a une categorie")
    public Produit createProduct(
            @ToolParam(description = "Nom commercial du produit", required = true) String nom,
            @ToolParam(description = "Prix de vente en dinars (positif)", required = true) double prix,
            @ToolParam(description = "Quantite initiale en stock", required = true) int quantite,
            @ToolParam(description = "Description detaillee du produit", required = true) String description,
            @ToolParam(description = "Nom de la categorie du produit (optionnel)", required = false) String nomCategorie) {

        // 1. Validation défensive
        if (nom == null || nom.isBlank()) {
            throw new IllegalArgumentException("Le nom du produit est obligatoire.");
        }
        if (prix <= 0) {
            throw new IllegalArgumentException("Le prix doit etre strictement superieur a 0.");
        }
        if (quantite < 0) {
            throw new IllegalArgumentException("La quantite ne peut pas etre negative.");
        }

        Produit p = new Produit();
        p.setNom(nom);
        p.setPrix(prix);
        p.setQuantite(quantite);
        p.setDescription(description);

        if (nomCategorie != null && !nomCategorie.isBlank()) {
            Categorie c = categorieRepository.findByNomIgnoreCase(nomCategorie);
            if (c == null) {
                throw new IllegalArgumentException("La categorie '" + nomCategorie + "' n'existe pas.");
            }
            p.setCategorie(c);
        }

        return produitRepository.save(p);
    }
}
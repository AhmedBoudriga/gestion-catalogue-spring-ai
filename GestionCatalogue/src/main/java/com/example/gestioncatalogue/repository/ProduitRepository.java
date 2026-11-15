package com.example.gestioncatalogue.repository;

import com.example.gestioncatalogue.entities.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProduitRepository extends JpaRepository<Produit, Long> {

    List<Produit> findByNomContains(String mc);

    @Query("select p from Produit p where p.nom like %:x%")
    List<Produit> rechercherParMotcle(@Param("x") String mc);
}
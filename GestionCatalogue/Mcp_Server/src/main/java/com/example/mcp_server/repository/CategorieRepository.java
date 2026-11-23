package com.example.mcp_server.repository;

import com.example.mcp_server.entities.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategorieRepository extends JpaRepository<Categorie,Long> {
    Categorie findByNomIgnoreCase(String nom);
}

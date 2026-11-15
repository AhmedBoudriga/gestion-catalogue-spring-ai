package com.example.gestioncatalogue;

import com.example.gestioncatalogue.entities.Categorie;
import com.example.gestioncatalogue.entities.Produit;
import com.example.gestioncatalogue.repository.CategorieRepository;
import com.example.gestioncatalogue.repository.ProduitRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GestionCatalogueApplication {
    //@Bean
    CommandLineRunner commandLineRunner(CategorieRepository categorieRepository, ProduitRepository produitRepository){
        return  arg ->{
            Categorie c1 = new Categorie(null,"informatique",null);
            Categorie c2 = Categorie.builder()
                    .nom("Java")
                    .build();
            categorieRepository.save(c1);
            categorieRepository.save(c2);

            produitRepository.save(Produit.builder().nom("pc").quantite(10).prix(20).categorie(c1).build());
            produitRepository.save(Produit.builder().nom("sourix").quantite(300).prix(205).categorie(c1).build());
            produitRepository.save(Produit.builder().nom("Intellij").quantite(300).prix(50).categorie(c2).build());

        } ;
    }

    public static void main(String[] args) {
        SpringApplication.run(GestionCatalogueApplication.class, args);
    }

}

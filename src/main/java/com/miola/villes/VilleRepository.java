package com.miola.villes;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VilleRepository extends JpaRepository<VilleModel, Integer> {

    //List<VilleModel> findVilleModelByVille_name(String name);

    //Optional<VilleModel> findByVille_name(String name);

}

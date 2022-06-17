package com.miola.villes;

import com.miola.villes.VilleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface VilleRepository extends JpaRepository<VilleModel, Integer> {

    //List<VilleModel> findVilleModelByVille_name(String name);

}

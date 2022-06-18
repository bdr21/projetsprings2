package com.miola.villes;


import com.miola.endroits.EndroitModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VilleRepository extends JpaRepository<VilleModel, Integer> {



    Optional<VilleModel> findByVillename(String name);

}

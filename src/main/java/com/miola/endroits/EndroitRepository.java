package com.miola.endroits;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EndroitRepository extends JpaRepository<EndroitModel, Integer> {

    List<EndroitModel> findEndroitModelByVille(int id);

    Optional<EndroitModel> findEndroitModelByName(String name);
}

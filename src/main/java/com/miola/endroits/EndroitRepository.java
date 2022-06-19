package com.miola.endroits;

import lombok.ToString;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EndroitRepository extends JpaRepository<EndroitModel, Integer> {

    Optional<EndroitModel> findByName(String name);

//    @Query("UPDATE EndroitModel e SET e.ratingAvg = ?1 WHERE e.id = ?2")
//    void updateRatingAvg(float ratingAvg, int id);


}

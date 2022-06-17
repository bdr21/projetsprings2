package com.miola.endroit_crud.Repo;


import com.miola.endroit_crud.Models.Endroit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EndroitRepository extends JpaRepository<Endroit, Long> {
}

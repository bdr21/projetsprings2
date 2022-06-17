package com.miola.villes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VilleService {

    @Autowired
    private VilleRepository villeRepository;

    public List<VilleModel> getAll() {
        return villeRepository.findAll();
    }


    public VilleModel save(VilleModel villeModel) {
        return villeRepository.save(villeModel);
    }


}

package com.miola.endroits;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EndroitService {

    @Autowired
    private EndroitRepository endroitRepository;

    public Optional<EndroitModel> getOneById(int id){ return endroitRepository.findById(id); }

    public List<EndroitModel> getAll() {
        return endroitRepository.findAll();
    }

    public void addOne(EndroitModel endroitModel){ endroitRepository.save(endroitModel);}

    public void update(EndroitModel endroitModel){ endroitRepository.save(endroitModel); }

    public void delete(int id){ endroitRepository.deleteById(id); }
}

package com.miola.endroits;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EndroitService {

    @Autowired
    private EndroitRepository endroitRepository;

    public Optional<EndroitModel> getOneById(int id){ return endroitRepository.findById(id); }

    // sort endroits by one of the fields
    public List<EndroitModel> findEndroitsWithSorting(String field){
        return endroitRepository.findAll(Sort.by(Sort.Direction.ASC, field));
    }

    // returns just pageSize endroits from the specified offset
    public Page<EndroitModel> findEndroitsWithPagination(int offset, int pageSize){
        return endroitRepository.findAll(PageRequest.of(offset, pageSize));
    }

    // returns just pageSize endroits from the specified offset sorted by one of the fields
    public Page<EndroitModel> findEndroitsWithPaginationAndSorting(int offset, int pageSize, String field){
        Page<EndroitModel> endroitModelPage = endroitRepository.findAll(PageRequest.of(offset, pageSize, Sort.by(Sort.Direction.ASC, field))); // .withSort(Sort.by(Sort.Direction.ASC, field))
        return endroitModelPage;
    }

    public List<EndroitModel> getAll() {
        return endroitRepository.findAll();
    }

    public void addOne(EndroitModel endroitModel){ endroitRepository.save(endroitModel);}

    public void update(EndroitModel endroitModel){ endroitRepository.save(endroitModel); }

    public void delete(int id){ endroitRepository.deleteById(id); }

}

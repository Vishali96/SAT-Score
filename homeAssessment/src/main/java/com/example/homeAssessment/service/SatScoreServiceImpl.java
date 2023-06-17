package com.example.homeAssessment.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.homeAssessment.model.SatScore;
import com.example.homeAssessment.repository.SatScoreRepository;

@Service
public class SatScoreServiceImpl implements SatScoreService {

    @Autowired
    private SatScoreRepository satScoreRepository;

    @Override
    public List < SatScore > getAllSatScores() {
        return satScoreRepository.findAll();
    }

    @Override
    public void saveSatScore(SatScore satScore) {
        this.satScoreRepository.save(satScore);
    }

    @Override
    public Integer getFindByRank(String name) {
        return satScoreRepository.findByRank(name);
    }
    @Override
    public Boolean getExistsByName(String name) {
        return satScoreRepository.existsByName(name);
    }

    @Override
    public SatScore getSatScoreById(long id) {
        Optional < SatScore > optional = satScoreRepository.findById(id);
        SatScore satScore = null;
        if (optional.isPresent()) {
            satScore = optional.get();
        } else {
            throw new RuntimeException(" No record for id :: " + id);
        }
        return satScore;
    }

    @Override
    public SatScore getSatScoreByName(String name) {
        Optional < SatScore > optional = satScoreRepository.findByName(name);
        SatScore satScore = null;
        if (optional.isPresent()) {
            satScore = optional.get();
        } else {
            throw new RuntimeException(" No record for name :: " + name);
        }
        return satScore;
    }
    

    @Override
    public void deleteSatScoreById(long id) {
        this.satScoreRepository.deleteById(id);
    }
}

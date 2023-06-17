package com.example.homeAssessment.service;

import java.util.List;

import com.example.homeAssessment.model.SatScore;

public interface SatScoreService {
    List < SatScore > getAllSatScores();
    void saveSatScore(SatScore satScore);
    SatScore getSatScoreById(long id);
    void deleteSatScoreById(long id);
    Boolean  getExistsByName(String name);
    Integer getFindByRank(String name);
    SatScore getSatScoreByName(String name);

    
    
}

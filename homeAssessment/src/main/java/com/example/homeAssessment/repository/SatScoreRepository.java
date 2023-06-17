package com.example.homeAssessment.repository;



import java.util.Optional;

// import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.homeAssessment.model.SatScore;

@Repository
public interface SatScoreRepository extends JpaRepository<SatScore, Long>{

  
    // Creating Custom Query to get Rank for the given name
    @Query(value = "select s.salesRank from (select name, RANK() over ( order by sat_score desc) salesRank from sat_scores) as s  where s.name like ?", nativeQuery = true)
    public  Integer findByRank(String name);
    // Creating Repository to check whether the name already exists or not
    Boolean  existsByName(String name);
    Optional <SatScore> findByName(String name);
    
}

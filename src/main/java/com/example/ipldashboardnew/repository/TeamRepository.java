package com.example.ipldashboardnew.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.ipldashboardnew.model.Team;

public interface TeamRepository extends CrudRepository<Team,Long>{
    Team findByTeamName(String teamName);
}

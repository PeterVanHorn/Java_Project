// Peter Van Horn
// JavaII Final Project
// 12/12/2023
// this is the implementation of the repository interface. provides interface for SprinklerProgramRepositoryImpl


package com.example.demo.db;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.SprinklerProgram;

public interface SprinklerProgramRepository extends JpaRepository<SprinklerProgram, Long>, SprinklerProgramCustomRepository{
	SprinklerProgram findByProgramName(String programName);

}

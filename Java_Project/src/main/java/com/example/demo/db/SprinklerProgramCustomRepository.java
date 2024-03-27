// Peter Van Horn
// JavaII Final Project
// 12/12/2023
// this is the repository interface. defines methods that the implementation needs to include



package com.example.demo.db;

import com.example.demo.domain.SprinklerProgram;

public interface SprinklerProgramCustomRepository {
	public SprinklerProgram findOneWithDetails(long id);
	public SprinklerProgram populateOneWithDetails(SprinklerProgram sprinklerProgram);

}

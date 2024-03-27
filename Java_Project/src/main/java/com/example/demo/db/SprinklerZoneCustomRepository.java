// Peter Van Horn
// JavaII Final Project
// 12/12/2023
// this is the zone repository interface, code borrowed from JPAMVC lab



package com.example.demo.db;

import com.example.demo.domain.SprinklerZone;

public interface SprinklerZoneCustomRepository {
	public SprinklerZone findDetails(long id);
	public SprinklerZone populateDetails(SprinklerZone sprinklerZone);

}

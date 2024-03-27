// Peter Van Horn
// JavaII Final Project
// 12/12/2023
// this is the repository interface, code borrowed from JPAMVC lab



package com.example.demo.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.SprinklerZone;

public interface SprinklerZoneRepository extends JpaRepository<SprinklerZone, Long>{
	List<SprinklerZone> findByZoneName(String zoneName); 
	List<SprinklerZone> findByZoneType(String zoneType); 

}

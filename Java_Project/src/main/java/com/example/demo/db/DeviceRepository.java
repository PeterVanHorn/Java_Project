// Peter Van Horn
// JavaII Final Project
// 12/12/2023
// device repository interface. Code borrowed from JPAMVC lab
// also not needed


package com.example.demo.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.domain.Device;


public interface DeviceRepository extends JpaRepository<Device, Long>, DeviceCustomRepository {
	List<Device> findAllWithDetail();
	
	@Query("SELECT COUNT(p.sprinklerProgramId) FROM SprinklerProgram p WHERE p.device = ?1") 
	Long getProgramCount2(Device device);

}

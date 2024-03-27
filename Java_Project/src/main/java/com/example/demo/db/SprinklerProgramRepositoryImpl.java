// Peter Van Horn
// JavaII Final Project
// 12/12/2023
// this is the implementation of the repository interface. contains methods to fetch and populate programs from and to repository



package com.example.demo.db;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.domain.SprinklerProgram;
import com.example.demo.domain.Device;


public class SprinklerProgramRepositoryImpl implements SprinklerProgramCustomRepository{
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	SprinklerProgramRepository sprinklerProgramRepository;
	
	public SprinklerProgram findOneWithDetails(long id) {
		SprinklerProgram sprinklerProgram = null;

		sprinklerProgram = sprinklerProgramRepository.findById(id).orElse(null);
		Query query = em.createQuery("SELECT d FROM Device d, Program p WHERE d = p.device AND p = ?1");
		query.setParameter(1, sprinklerProgram);
		Device device = (Device) query.getSingleResult();
		sprinklerProgram.setDevice(device);

		return sprinklerProgram;
	}

	public SprinklerProgram populateOneWithDetails(SprinklerProgram sprinklerProgram) {
		Query query = em.createQuery("SELECT d FROM Device d, Program p WHERE d = p.device AND p = ?1");
		query.setParameter(1, sprinklerProgram);
		Device device = (Device) query.getSingleResult();
		sprinklerProgram.setDevice(device);

		return sprinklerProgram;
	}
}

// Peter Van Horn
// JavaII Final Project
// 12/12/2023
// this is the implementation of the zone custom repository mostly code borrowed from JPAMVC lab




package com.example.demo.db;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.domain.SprinklerProgram;
import com.example.demo.domain.SprinklerZone;




public class SprinklerZoneRepositoryImpl implements SprinklerZoneCustomRepository{
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	SprinklerZoneRepository sprinklerZoneRepository;
	
	public SprinklerZone findDetails(long id) {
		SprinklerZone sprinklerZone = null;
		
		sprinklerZone = sprinklerZoneRepository.findById(id).orElse(null);
		Query query = em.createQuery("SELECT p FROM SprinklerProgram p, SprinklerZone l WHERE r = l.sprinklerProgram AND l = ?1");
		query.setParameter(1, sprinklerZone);
		SprinklerProgram sprinklerProgram = (SprinklerProgram) query.getSingleResult();
		sprinklerZone.setSprinklerProgram(sprinklerProgram);
		
		return sprinklerZone;
	}
	
	public SprinklerZone populateDetails(SprinklerZone sprinklerZone) {
		Query query = em.createQuery("SELECT p FROM SprinklerProgram p, SprinklerZone l WHERE r = l.sprinklerProgram AND l = ?1");
		query.setParameter(1, sprinklerZone);
		SprinklerProgram sprinklerProgram = (SprinklerProgram) query.getSingleResult();
		sprinklerZone.setSprinklerProgram(sprinklerProgram);
		
		return sprinklerZone;
	}
}

// Peter Van Horn
// JavaII Final Project
// 12/12/2023
// device repository implementation. Code borrowed from JPAMVC lab
// also not needed



package com.example.demo.db;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.Device;
import com.example.demo.domain.SprinklerProgram;

public class DeviceRepositoryImpl implements DeviceCustomRepository{
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	DeviceRepository deviceRepository;
	
	public Device findOneWithDetails(long id) {
		Device device = null;

		device = deviceRepository.findById(id).orElse(null);
		Query query = em.createQuery("SELECT p FROM Program p WHERE p.Device = ?1");
		query.setParameter(1, device);
		List<SprinklerProgram> programs = (List<SprinklerProgram>) query.getResultList();
		device.setPrograms(programs);

		return device;
	}

	public Device populateOneWithDetails(Device device) {
		Query query = em.createQuery("SELECT c FROM Country c WHERE c.Device = ?1");
		query.setParameter(1, device);
		List<SprinklerProgram> programs = (List<SprinklerProgram>) query.getResultList();
		device.setPrograms(programs);

		return device;
	}
	
	public long getProgramCount(long deviceId) {
		long result = -1;
		Query query = em.createQuery("SELECT COUNT(p.sprinklerProgramId) FROM SprinklerProgram p WHERE p.Device.DeviceId = ?1 ");
		query.setParameter(1, deviceId);
		result = ((Long)query.getSingleResult()).longValue();
		return result;
		}
	
	@Transactional
	public void transferProgramToOtherDevice(SprinklerProgram sprinklerProgram, Device currentDevice, Device newDevice) {

		currentDevice = populateOneWithDetails(currentDevice);
		newDevice = populateOneWithDetails(newDevice);
		currentDevice.getPrograms().remove(sprinklerProgram);
		newDevice.getPrograms().add(sprinklerProgram);
		sprinklerProgram.setDevice(newDevice);
		em.merge(currentDevice);
		em.merge(sprinklerProgram);
		em.merge(newDevice);
		
	}

}

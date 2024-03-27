// Peter Van Horn
// JavaII Final Project
// 12/12/2023
// this is the zoneController class, code borrowed from JPAMVC lab
// contains all the methods for a zone




package com.example.demo;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

//import javax.servlet.http.HttpServletResponse;
//import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.demo.db.SprinklerProgramRepository;
import com.example.demo.db.SprinklerZoneRepository;
import com.example.demo.domain.SprinklerProgram;
import com.example.demo.domain.SprinklerZone;

@Controller
@RequestMapping("/sprinklerZone")
public class ZoneController {
	
	private SprinklerZoneRepository sprinklerZoneRepository;
	private SprinklerProgramRepository sprinklerProgramRepository;
	
	@Autowired
	public ZoneController(SprinklerZoneRepository sprinklerZoneRepository, SprinklerProgramRepository sprinklerProgramRepository) {
		this.sprinklerZoneRepository = sprinklerZoneRepository;
		this.sprinklerProgramRepository = sprinklerProgramRepository;
	}
	
	
	// api get method for zone list
	@RequestMapping(value="/api", method=GET)
	public @ResponseBody List<SprinklerZone> getSprinklerZoneList(Model model) {
		List<SprinklerZone> zoneList = sprinklerZoneRepository.findAll();
		return zoneList;
	}
	
	
	// api get method for specific zone
	@RequestMapping(value="/api/{id}", method=GET)
	public @ResponseBody SprinklerZone getSprinklerZoneById(@PathVariable Long id) {
		SprinklerZone sprinklerZone = sprinklerZoneRepository.findById(id).orElse(null);
		System.err.println("Zone: " + sprinklerZone.getZoneName());
		return sprinklerZone;
	}
	
	// api post method for a zone
	@RequestMapping(value="/api/create", method=POST)
	public @ResponseBody SprinklerZone submitSprinklerZoneApi( @RequestBody @Valid ZoneForm zoneForm, Errors errors) {
		System.err.println(zoneForm.getZoneName());
		System.err.println("Has errors: " + errors.hasErrors());
		SprinklerZone sprinklerZone = new SprinklerZone();
		sprinklerZone.setZoneName(zoneForm.getZoneName());
		sprinklerZone.setZoneType(zoneForm.getZoneType());
		sprinklerZone.setRunTime(zoneForm.getRunTime());

		SprinklerProgram sprinklerProgram = sprinklerProgramRepository.findById(zoneForm.getSprinklerProgramId()).orElse(null);
		
		sprinklerZone.setSprinklerProgram(sprinklerProgram);
		sprinklerZone = sprinklerZoneRepository.save(sprinklerZone);
		
		return sprinklerZone;
	}
	
	// get method for zonelist, returns zonelist view
	@RequestMapping(value="/list", method=GET)
	public String getSprinklerZones(Model model) {
      List<SprinklerZone> sprinklerZones = sprinklerZoneRepository.findAll();
	  model.addAttribute("sprinklerZones", sprinklerZones);
	  System.err.println("SIZE: " + sprinklerZones.size());
	  for(SprinklerZone l: sprinklerZones)
		  System.err.println("Name: " + l.getSprinklerZoneId());
	  return "zone/zonelist";
	}
	
	// get method for specific zone, returns detail view of selected zone
	@RequestMapping(value="/{id}", method=GET)
	public String getLocationById(@PathVariable Long id, Model model) {
		SprinklerZone sprinklerZone = sprinklerZoneRepository.findById(id).orElse(null);
	  model.addAttribute("zon", sprinklerZone);
	  System.err.println("Zone Name: " + sprinklerZone.getZoneName());
	  return "zone/details";
	}
	
	// get method for zone, returns add view to create a new zone
	@RequestMapping(value="/add", method=GET)
	public String showCreateForm(Model model) {
      List<SprinklerProgram> sprinklerPrograms = sprinklerProgramRepository.findAll();
      model.addAttribute("sprinklerPrograms", sprinklerPrograms);
	  model.addAttribute("zoneForm", new ZoneForm());	
	  return "zone/add";
	}
	
	
	// post method for zone, creates zone object and populates it with zoneform, then saves to database and returns detail view for new zone
	@RequestMapping(value="/create", method=POST)
	public String submitZone( @Valid ZoneForm zoneForm, Errors errors) {
		System.err.println(zoneForm.getZoneName());
		System.err.println("Has errors: " + errors.hasErrors()); 
		SprinklerZone sprinklerZone = new SprinklerZone();
		sprinklerZone.setZoneName(zoneForm.getZoneName());
		
		SprinklerProgram sprinklerProgram = sprinklerProgramRepository.findById(zoneForm.getSprinklerProgramId()).orElse(null);
				
		if (errors.hasErrors() | sprinklerProgram == null) {
		    return "sprinklerZone/create";
		}
		sprinklerZone.setZoneName(zoneForm.getZoneName());
		sprinklerZone.setZoneType(zoneForm.getZoneType());
		sprinklerZone.setRunTime(zoneForm.getRunTime());
		sprinklerZone.setIsRun(zoneForm.getIsRun());
		sprinklerZone.setSprinklerProgram(sprinklerProgram);
		sprinklerZone = sprinklerZoneRepository.save(sprinklerZone);
	 
	  return "redirect:/sprinklerZone/" + sprinklerZone.getSprinklerZoneId();
	  
	}

	// get method for editing a zone, populates zone edit form and then returns edit view 
	@RequestMapping(value="/edit/{id}", method=GET)
	public String showEditForm(@PathVariable Long id, Model model) {
	  SprinklerZone sprinklerZone = sprinklerZoneRepository.findById(id).orElse(null);
      List<SprinklerProgram> programs = sprinklerProgramRepository.findAll();
      model.addAttribute("programs", programs);
      ZoneEditForm form =  new ZoneEditForm();
      form.setSprinklerZoneId(sprinklerZone.getSprinklerZoneId());
      form.setZoneName(sprinklerZone.getZoneName());
      form.setZoneType(sprinklerZone.getZoneType());
      form.setRunTime(sprinklerZone.getRunTime());
      form.setIsRun(sprinklerZone.getIsRun());
      form.setSprinklerProgramId(sprinklerZone.getSprinklerProgram().getSprinklerProgramId());
      
	  model.addAttribute("zoneEditForm", form);	
	  return "zone/edit";
	}
	
	// api post method for editing a zone, returns a zone object
	@RequestMapping(value="api/edit/{id}", method=POST)
	public @ResponseBody SprinklerZone editSprinklerZoneApi(@PathVariable Long id, @RequestBody @Valid ZoneEditForm zoneEditForm) {
	  SprinklerZone sprinklerZone = sprinklerZoneRepository.findById(id).orElse(null);
	  sprinklerZone.setSprinklerZoneId(zoneEditForm.getSprinklerZoneId());
	  sprinklerZone.setZoneName(zoneEditForm.getZoneName());
	  sprinklerZone.setZoneType(zoneEditForm.getZoneType());
	  sprinklerZone.setRunTime(zoneEditForm.getRunTime());
	  sprinklerZone.setIsRun(zoneEditForm.getIsRun());

      SprinklerProgram sprinklerProgram = sprinklerProgramRepository.findById(zoneEditForm.getSprinklerProgramId()).orElse(null);
      
      sprinklerZone.setSprinklerProgram(sprinklerProgram);
      sprinklerZone = sprinklerZoneRepository.save(sprinklerZone);

	  return sprinklerZone;
	}
	
	
	// post method for editing a zone, finds zone to be changed, then replaces its attributes with the data from the zone edit form
	// then returns details for the edited zone
	@RequestMapping(value="/submitedit", method=POST)
	public String submitEditSprinklerZone( @Valid ZoneEditForm zoneEditForm, Errors errors) {
		System.err.println(zoneEditForm.getZoneName());
		System.err.println("Has errors: " + errors.hasErrors()); 
		SprinklerZone sprinklerZone = sprinklerZoneRepository.findById(zoneEditForm.getSprinklerZoneId()).orElse(null);
		sprinklerZone.setZoneName(zoneEditForm.getZoneName());
		sprinklerZone.setZoneType(zoneEditForm.getZoneType());
		sprinklerZone.setRunTime(zoneEditForm.getRunTime());
		sprinklerZone.setIsRun(zoneEditForm.getIsRun());
		
		SprinklerProgram sprinklerProgram = sprinklerProgramRepository.findById(zoneEditForm.getSprinklerProgramId()).orElse(null);
				
		if (errors.hasErrors() | sprinklerProgram == null) {
		    return "sprinklerZone/create";
		}
		sprinklerZone.setSprinklerProgram(sprinklerProgram);
		sprinklerZone = sprinklerZoneRepository.save(sprinklerZone);
	 
	  return "redirect:/sprinklerZone/" + sprinklerZone.getSprinklerZoneId();
	  
	}
	
	// get method for running a zone. receives zone info from zonelist view, returns run view, then sets isRun attribute to true, 
	// sleeps the thread for runtime then sets the isRun back to false.
	@RequestMapping(value="/run/{id}", method=GET)
	public String showRunForm(@PathVariable Long id, Model model) {
		SprinklerZone sprinklerZone = sprinklerZoneRepository.findById(id).orElse(null);
		System.err.println(sprinklerZone.getZoneName() + " running");
		sprinklerZone.setIsRun(true);
		sprinklerZoneRepository.save(sprinklerZone);
		Long time = sprinklerZone.getRunTime()*60*1000;
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.err.println(sprinklerZone.getZoneName() + " run complete");
		sprinklerZone.setIsRun(false);
		sprinklerZoneRepository.save(sprinklerZone);
//		Timer timer = new Timer();
//		timer.schedule(new TimerTask(){
//			@Override
//			public void run() {
//				System.err.println(sprinklerZone.getZoneName() + " run complete");
//				sprinklerZone.setIsRun(false);
//				sprinklerZoneRepository.save(sprinklerZone);
//			}
//		}, time);
		model.addAttribute("zon", sprinklerZone);
		model.addAttribute(sprinklerZone.getRunTime());
	  return "zone/run";
	}
	
	// api post method for creating and saving new zone to database
	@RequestMapping(value="/api", method=POST)
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody SprinklerZone postSprinklerZone( @RequestBody SprinklerZone sprinklerZone, HttpServletResponse response) {
		System.err.println(sprinklerZone.getZoneName());
		
		SprinklerProgram sprinklerProgram = sprinklerProgramRepository.findById(sprinklerZone.getSprinklerProgram().getSprinklerProgramId()).orElse(null);
		sprinklerZone.setSprinklerProgram(sprinklerProgram);

		sprinklerZone = sprinklerZoneRepository.save(sprinklerZone);

	  return sprinklerZone;
	}

}

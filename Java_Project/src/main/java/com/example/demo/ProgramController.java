// Peter Van Horn
// JavaII Final Project
// 12/12/2023
// this is the programController class, code borrowed from JPAMVC lab
// contains all the methods for a program

package com.example.demo;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

//import javax.servlet.http.HttpServletResponse;
//import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.demo.db.DeviceRepository;
import com.example.demo.db.SprinklerProgramRepository;
import com.example.demo.db.SprinklerZoneRepository;
import com.example.demo.domain.Device;
import com.example.demo.domain.SprinklerProgram;
import com.example.demo.domain.SprinklerZone;

@Controller
@RequestMapping("/sprinklerProgram")
public class ProgramController {
	
	private SprinklerProgramRepository sprinklerProgramRepository;
	private DeviceRepository deviceRepository;
	
	//added in an effort to get program run method to work
	private SprinklerZoneRepository sprinklerZoneRepository;
	
	@Autowired
	public ProgramController(SprinklerProgramRepository sprinklerProgramRepository, DeviceRepository deviceRepository, SprinklerZoneRepository sprinklerZoneRepository) {
		this.sprinklerProgramRepository = sprinklerProgramRepository;
		this.deviceRepository = deviceRepository;
		//same addition
		this.sprinklerZoneRepository = sprinklerZoneRepository;
	}
	
	// api get method for program list. returns list of programs
	@RequestMapping(value="/api", method=GET)
	public @ResponseBody List<SprinklerProgram>  getProgramList(Model model) {
      List<SprinklerProgram> programList = sprinklerProgramRepository.findAll();
	  return programList;
	}
	
	// api get method for specific program. returns program based on id provided by user
	@RequestMapping(value="/api/{id}", method=GET)
	public @ResponseBody SprinklerProgram getProgramById(@PathVariable Long id) {
		SprinklerProgram sprinklerProgram = sprinklerProgramRepository.findById(id).orElse(null);
	  System.err.println("SIZE: " + sprinklerProgram.getSprinklerZones().size());
	  for(SprinklerZone l: sprinklerProgram.getSprinklerZones())
		  System.err.println("Zone name: " + l.getZoneName());
	  return sprinklerProgram;
	}
	
	// api post method to create program. takes programForm and creates new program and saves to database. returns that program
	@RequestMapping(value="/api/create", method=POST)
	public @ResponseBody SprinklerProgram submitProgramApi( @RequestBody @Valid ProgramForm programForm, Errors errors) {
		System.err.println(programForm.getProgramName());
		System.err.println("Has errors: " + errors.hasErrors()); 
		SprinklerProgram sprinklerProgram = new SprinklerProgram();
		sprinklerProgram.setProgramName(programForm.getProgramName());
		Device device = deviceRepository.findById(programForm.getDeviceId()).orElse(null);
		sprinklerProgram.setDevice(device);
		sprinklerProgram = sprinklerProgramRepository.save(sprinklerProgram);
		return sprinklerProgram;
	}
	
	// list get method to list programs. collects all programs from repository if there are any and then returns programlist view
	@RequestMapping(value="/list", method=GET)
	public String getPrograms(Model model) {
      List<SprinklerProgram> programs = sprinklerProgramRepository.findAll();
	  model.addAttribute("programs", programs);
	  System.err.println("SIZE: " + programs.size());
	  for(SprinklerProgram p: programs)
		  System.err.println("Name: " + p.getSprinklerProgramId());
	  return "program/sprinklerProgramlist";
	}
	
	// get method for specific program. finds program in repository according to user provided id, creates a model and then returns
	// detail view to display that model
	@RequestMapping(value="/{id}", method=GET)
	public String getSprinklerProgramById(@PathVariable Long id, Model model) {
      SprinklerProgram sprinklerProgram = sprinklerProgramRepository.findById(id).orElse(null);
	  model.addAttribute("pro", sprinklerProgram);
	  model.addAttribute("zoneCount", sprinklerProgram.getSprinklerZones().size());
	  System.err.println("SIZE: " + sprinklerProgram.getSprinklerZones().size());
	  for(SprinklerZone l: sprinklerProgram.getSprinklerZones())
		  System.err.println("Zone name: " + l.getZoneName());
	  return "program/details";
	}
	
	// post method for adding program. creates model and form then returns add view for user to populate form
	@RequestMapping(value="/add", method=GET)
	public String showCreateForm(Model model) {
		List<Device> devices = deviceRepository.findAll();
	  model.addAttribute("devices", devices);
	  model.addAttribute("programForm", new ProgramForm());	
	  return "program/add";
	}
	
	// create post method for program. creates program object then populates it with details from the form
	// saves object to database and returns detail of the created program
	@RequestMapping(value="/create", method=POST)
	public String submitSprinklerProgram( @Valid ProgramForm programForm, Errors errors) {
		System.err.println(programForm.getProgramName());
		System.err.println(programForm.getStartTime());
		System.err.println("Has errors: " + errors.hasErrors()); 
		SprinklerProgram sprinklerProgram = new SprinklerProgram();
		sprinklerProgram.setProgramName(programForm.getProgramName());
		sprinklerProgram.setStartTime(programForm.getStartTime());
		sprinklerProgram.setIsRun(programForm.getIsRun());
		
		Device device = deviceRepository.findById(programForm.getDeviceId()).orElse(null);
				
		if (errors.hasErrors() | device == null) {
		    return "program/create";
		}
		sprinklerProgram.setDevice(device);
		sprinklerProgram = sprinklerProgramRepository.save(sprinklerProgram);
	 
	  return "redirect:/sprinklerProgram/" + sprinklerProgram.getSprinklerProgramId();
	  
	}

	// get method for editing a program. takes user provided id and model of requested program. creates new editform then fills it with 
	// existing programs attributes and creates a model from the form and returns edit view for user to modify form
	@RequestMapping(value="/edit/{id}", method=GET)
	public String showEditForm(@PathVariable Long id, Model model) {
	  SprinklerProgram sprinklerProgram = sprinklerProgramRepository.findById(id).orElse(null);
	  List<Device> devices = deviceRepository.findAll();
      model.addAttribute("devices", devices);
      ProgramEditForm form = new ProgramEditForm();
      form.setSprinklerProgramId(sprinklerProgram.getSprinklerProgramId());
      form.setProgramName(sprinklerProgram.getProgramName());
      form.setStartTime(sprinklerProgram.getStartTime());
      form.setDeviceId(sprinklerProgram.getDevice().getDeviceId());
      form.setIsRun(sprinklerProgram.getIsRun());
      
	  model.addAttribute("programEditForm", form);	
	  return "program/edit";
	}
	
	// post method to submit edit. takes edit form, finds program from repository, replaces changed attributes, saves program to database
	// returns details for edited program
	@RequestMapping(value="/submitedit", method=POST)
	public String submitEditSprinklerProgram( @Valid ProgramEditForm programEditForm, Errors errors) {
		System.err.println(programEditForm.getProgramName());
		System.err.println("Has errors: " + errors.hasErrors()); 
		SprinklerProgram sprinklerProgram = sprinklerProgramRepository.findById(programEditForm.getSprinklerProgramId()).orElse(null);
		sprinklerProgram.setProgramName(programEditForm.getProgramName());
		sprinklerProgram.setIsRun(programEditForm.getIsRun());
		sprinklerProgram.setStartTime(programEditForm.getStartTime());
		Device device = deviceRepository.findById(programEditForm.getDeviceId()).orElse(null);
		if (errors.hasErrors() | device == null) {
		    return "sprinklerProgram/create";
		}
		sprinklerProgram.setDevice(device);
		sprinklerProgram = sprinklerProgramRepository.save(sprinklerProgram);
	  return "redirect:/sprinklerProgram/" + sprinklerProgram.getSprinklerProgramId();
	}
	
	// method to manually run a given program. finds requested program, returns program list view, sets its isRun to true, 
	// then runs all zones belonging to requested program sequentially, toggling each of their isRun attributes, 
	// once zones have all run, toggles program isRun back to false.
	@RequestMapping(value="/run/{id}", method=GET)
	public String showRunForm(@PathVariable Long id, Model model) throws ParseException {
		SprinklerProgram sprinklerProgram = sprinklerProgramRepository.findById(id).orElse(null);
		System.err.println(LocalTime.now());
	    sprinklerProgram.setIsRun(true);
	    sprinklerProgramRepository.save(sprinklerProgram);
		for(SprinklerZone x: sprinklerProgram.getSprinklerZones()) {
			Long time = x.getRunTime()*60*1000;
			System.err.println(x.getZoneName() + " running");
			x.setIsRun(true);
			sprinklerZoneRepository.save(x);
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.err.println(x.getZoneName() + " complete");
			x.setIsRun(false);
			sprinklerZoneRepository.save(x);
		}
	    sprinklerProgram.setIsRun(false);
	    sprinklerProgramRepository.save(sprinklerProgram);
		model.addAttribute("pro", sprinklerProgram);
		model.addAttribute(sprinklerProgram.getStartTime());
	  return "program/sprinklerProgramlist";
	}
	
	// Auto method, I put a button on the nav bar to start auto mode. 
	// Any data altered after the auto toggle will not be taken into account for the function of the auto method.
	// I will change this eventually, but I don't have time for it now.
	@RequestMapping(value="/run", method=GET)
	public String autoRun(Model model) throws ParseException {
		List <SprinklerProgram> sprinklerPrograms = sprinklerProgramRepository.findAll();
		boolean progRunning = false;
		while(!progRunning) {
			for(SprinklerProgram x: sprinklerPrograms) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Long duration = 0L;
				for (SprinklerZone z : x.getSprinklerZones()) {
					duration += z.getRunTime();
				}
				System.err.println(LocalTime.now());
					if(!x.getIsRun() && LocalTime.now().isAfter(x.getStartTime()) && LocalTime.now().isBefore(x.getStartTime().plusMinutes(duration))) {
						x.setIsRun(true);
						sprinklerProgramRepository.save(x);
						for(SprinklerZone y: x.getSprinklerZones()) {
							Long time = y.getRunTime()*60*1000;
							System.err.println(y.getZoneName() + " running");
							y.setIsRun(true);
							sprinklerZoneRepository.save(y);
							try {
								Thread.sleep(time);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							System.err.println(y.getZoneName() + " complete");
							y.setIsRun(false);
							sprinklerZoneRepository.save(y);
						}
						x.setIsRun(false);
						sprinklerProgramRepository.save(x);
					}
			}
		}
		return "program/sprinklerProgramlist";
	}

	// api post method for program finds program device and saves attributes. returns program object
	@RequestMapping(value="/api", method=POST)
	@ResponseStatus(HttpStatus.CREATED) 
	public @ResponseBody SprinklerProgram postSprinklerProgram( @RequestBody SprinklerProgram sprinklerProgram, HttpServletResponse response) {
		System.err.println(sprinklerProgram.getProgramName());
		Device device = deviceRepository.findById(sprinklerProgram.getDevice().getDeviceId()).orElse(null);
		sprinklerProgram.setDevice(device);
		sprinklerProgram = sprinklerProgramRepository.save(sprinklerProgram);
	  return sprinklerProgram;
	}
}
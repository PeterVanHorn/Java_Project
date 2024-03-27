// Peter Van Horn
// JavaII Final Project
// 12/12/2023
// zone creation form created from the ZoneController create method, code borrowed from JPAMVC lab



package com.example.demo;

import jakarta.validation.constraints.NotNull;

//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Data
public class ZoneForm {
	
	@NotNull
	private String zoneName;
	
	@NotNull
	private String zoneType;
	
	@NotNull
	private Long runTime;
	
	@NotNull
	private Boolean isRun;
	
	@NotNull
	private Long sprinklerProgramId;

}

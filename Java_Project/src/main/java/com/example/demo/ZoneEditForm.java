// Peter Van Horn
// JavaII Final Project
// 12/12/2023
// this is the zone edit form created by the edit method of the ZoneController class, code borrowed from JPAMVC lab




package com.example.demo;

import jakarta.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Data
public class ZoneEditForm {
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
	
	@NotNull
	private Long sprinklerZoneId;

}

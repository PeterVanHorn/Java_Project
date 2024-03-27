// Peter Van Horn
// JavaII Final Project
// 12/12/2023
// Form class for creating a new program. 


package com.example.demo;

import java.time.LocalTime;
import java.util.Date;

import jakarta.validation.constraints.NotNull;

//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Data
public class ProgramForm {
	
	@NotNull
	private String programName;
	
	@NotNull
	private LocalTime startTime;
	
	@NotNull
	private Boolean isRun;
	
	@NotNull
	private Long deviceId;
}

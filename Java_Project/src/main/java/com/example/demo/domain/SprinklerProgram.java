// Peter Van Horn
// JavaII Final Project
// 12/12/2023
// this is the Program class that outlines the attributes of a program and contains code to generate a unique programID

package com.example.demo.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;



import jakarta.persistence.*;

//import javax.persistence.Column;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.OneToMany;
//import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
@Table(name="PROGRAM")
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@Data
public class SprinklerProgram implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="SPRINKLERPROGRAM_ID", unique=true, nullable=false, length=2)
	private Long sprinklerProgramId;
	
	@Column(name="PROGRAM_NAME", length=40)
	private String programName;
	
	@Column(name="START_TIME")
	private LocalTime startTime;
	
	@Column(name="STATE")
	private Boolean isRun;
	
	@ManyToOne
	@JoinColumn(name="DEVICE_ID")
	private Device device;
	
	@OneToMany(mappedBy="sprinklerProgram", fetch = FetchType.EAGER)
	@JsonBackReference
	private List<SprinklerZone> sprinklerZones;

}

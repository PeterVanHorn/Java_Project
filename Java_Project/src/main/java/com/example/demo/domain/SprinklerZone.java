// Peter Van Horn
// JavaII Final Project
// 12/12/2023
// this is the Zone class that outlines the attributes of a zone and contains code to generate a unique zoneID




package com.example.demo.domain;

import java.io.Serializable;
import java.time.LocalTime;

import jakarta.persistence.*;

//import javax.persistence.*;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
@Table(name="ZONE")
//@NamedQuery(name="Location.findAll", query="SELECT l FROM Location l")
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@Data
public class SprinklerZone implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="SPRINKLERZONE_ID", unique=true, nullable=false, precision=4)
	private Long sprinklerZoneId;
	
	@Column(name="ZONE_NAME", length=12)
	private String zoneName;
	
	@Column(name="ZONE_TYPE", length=25)
	private String zoneType;
	
	@Column(name="RUN_TIME")
	private Long runTime;
	
	@Column(name="STATE")
	private Boolean isRun;
	
	@ManyToOne
	@JoinColumn(name="SPRINKLERPROGRAM_ID")
	private SprinklerProgram sprinklerProgram;
}

// Peter Van Horn
// JavaII Final Project
// 12/12/2023
// Device class. Each device has a name, id and list of programs. Code borrowed from JPAMVC lab
// I created this class to help me understand the concepts during creation of the application
// it is completely unnecessary and I should probably remove


package com.example.demo.domain;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.NamedQueries;
//import javax.persistence.NamedQuery;
//import javax.persistence.OneToMany;
//import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="DEVICE")
@NamedQueries({
	@NamedQuery(name="Device.findAll", query="SELECT d FROM Device d"),
		@NamedQuery(name="Device.findAllWithDetail", query="SELECT DISTINCT d FROM Device d LEFT JOIN FETCH d.programs p WHERE p.device = d") 
})
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@Data
public class Device implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="DEVICE_ID", unique=true, nullable=false, precision=22)
	private long deviceId;

	@Column(name="DEVICE_NAME", length=25)
	private String deviceName;

	@OneToMany(mappedBy="device", fetch = FetchType.LAZY)
	@JsonBackReference
	private List<SprinklerProgram> programs;
	
	public SprinklerProgram addProgram(SprinklerProgram program) {
		getPrograms().add(program);
		program.setDevice(this);

		return program;
	}

	public SprinklerProgram removeProgram(SprinklerProgram program) {
		getPrograms().remove(program);
		program.setDevice(null);

		return program;
	}
	

}

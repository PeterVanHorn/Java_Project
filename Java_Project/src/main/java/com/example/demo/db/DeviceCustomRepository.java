// Peter Van Horn
// JavaII Final Project
// 12/12/2023
// device repository interface. Code borrowed from JPAMVC lab
// also not needed



package com.example.demo.db;

import com.example.demo.domain.Device;
import com.example.demo.domain.SprinklerProgram;

public interface DeviceCustomRepository {
	public Device findOneWithDetails(long id);
	public Device populateOneWithDetails(Device device);
	public void transferProgramToOtherDevice(SprinklerProgram sprinklerProgram, Device currentDevice, Device newDevice);
	public long getProgramCount(long deviceId);

}

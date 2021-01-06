package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.model.Schedule;

import java.util.List;

public interface IScheduleService {

    Schedule saveSchedule(Schedule schedule);
    List<Schedule> getAllSchedules();
    List<Schedule> getSchedulesForPet(long petId);
    List<Schedule> getScheduleForEmployee(long employeeId);
    List<Schedule> getScheduleForCustomer(long customerId);
}

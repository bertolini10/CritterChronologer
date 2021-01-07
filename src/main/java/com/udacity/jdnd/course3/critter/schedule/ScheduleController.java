package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.model.Employee;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.model.Schedule;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.apache.catalina.mbeans.MBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    ScheduleService scheduleService;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    PetService petService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);

        List<Long> employeeIds = scheduleDTO.getEmployeeIds();
        List<Long> petIds = scheduleDTO.getPetIds();

        List<Employee> employees = employeeIds.stream().map(employeeId -> {
            Employee employee = employeeService.getEmployeeById(employeeId);
            return employee;
        }).collect(Collectors.toList());

        List<Pet> pets = petService.getAllPetsByIds(petIds);

        schedule.setEmployees(employees);
        schedule.setPets(pets);
        Schedule schedule1e = scheduleService.saveSchedule(schedule);
        return this.Schedule2DTO(schedule1e);
    }

    private Schedule Schedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);
        return schedule;
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> allSchedules = scheduleService.getAllSchedules();
        return allSchedules.stream().map(this::Schedule2DTO).collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<Schedule> schedulesForPet = scheduleService.getSchedulesForPet(petId);
        return schedulesForPet.stream().map(this::Schedule2DTO).collect(Collectors.toList());
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<Schedule> scheduleForEmployee = scheduleService.getScheduleForEmployee(employeeId);
        return scheduleForEmployee.stream().map(this::Schedule2DTO).collect(Collectors.toList());
    }


    private ScheduleDTO Schedule2DTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        Set<EmployeeSkill> skills;

        if(schedule.getActivities() != null){
            skills = new HashSet<EmployeeSkill>(schedule.getActivities());
        }else{
            skills = new HashSet<EmployeeSkill>();
        }

        scheduleDTO.setActivities(skills);
        scheduleDTO.setDate(schedule.getDate());
        List<Long> employeeIds = schedule.getEmployees().stream().map(Employee::getId).collect(Collectors.toList());
        scheduleDTO.setEmployeeIds(employeeIds);
        List<Long> petIds = schedule.getPets().stream().map(Pet::getId).collect(Collectors.toList());
        scheduleDTO.setPetIds(petIds);
        scheduleDTO.setId(schedule.getId());

        return scheduleDTO;
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<Schedule> schedulesForCustomer = scheduleService.getScheduleForCustomer(customerId);
        return    schedulesForCustomer.stream().map(this::Schedule2DTO).collect(Collectors.toList());
    }
}

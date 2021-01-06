package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.model.Customer;
import com.udacity.jdnd.course3.critter.model.Employee;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    CustomerService customerService;
    @Autowired
    EmployeeService employeeService;

    @Autowired
    PetService petService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){

        Customer customer = new Customer();
        customer.setName(customerDTO.getName());
        customer.setNotes(customerDTO.getNotes());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());

        List<Long> petIds = customerDTO.getPetIds();
        if(petIds != null) {
            List<Pet> pets = petService.getAllPetsByIds(petIds);
            customer.setPets(pets);
        }

        Customer customer1 = customerService.saveCustomer(customer);

        customerDTO.setId(customer1.getId());

        return customerDTO;


    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        throw new UnsupportedOperationException();
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        throw new UnsupportedOperationException();
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {

        Set<EmployeeSkill> skills = employeeDTO.getSkills();
        Set<DayOfWeek> daysAvailable = employeeDTO.getDaysAvailable();

        List<EmployeeSkill> Lskills;
        List<DayOfWeek> LdaysAvailable;

        if (skills != null) {
            Lskills = new ArrayList<>(skills);
        } else {
            Lskills = new ArrayList<>();
        }

        if (daysAvailable != null) {
            LdaysAvailable = new ArrayList<>(daysAvailable);
        } else {
            LdaysAvailable = new ArrayList<>();
        }
        Employee employee = new Employee();

        employee.setName(employeeDTO.getName());
        employee.setId(employeeDTO.getId());
        employee.setSkills(Lskills);
        employee.setDaysAvailable(LdaysAvailable);

        Employee savedEmployee = employeeService.saveEmployee(employee);
        employeeDTO.setId(savedEmployee.getId());

        return employeeDTO;


    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        throw new UnsupportedOperationException();
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        throw new UnsupportedOperationException();
    }

}

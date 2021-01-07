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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        List<Customer> customers = customerService.getAllCustomers();
        return customers.stream().map(this::Customer2DTO).collect(Collectors.toList());
    }

    public CustomerDTO Customer2DTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setPhoneNumber(customer.getPhoneNumber());
        customerDTO.setNotes(customer.getNotes());
        List<Long> petIds;
        if(customer.getPets() !=null){
            petIds = customer.getPets().stream().map(Pet::getId).collect(Collectors.toList());
        } else {
            petIds = new ArrayList<>();
        }
        customerDTO.setPetIds(petIds);
        return customerDTO;

    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        Pet pet = petService.getPetById(petId);
        return this.Customer2DTO( pet.getOwner());
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
        Employee employee = employeeService.getEmployeeById(employeeId);
        return this.Employee2DTO(employee);
    }

    public EmployeeDTO Employee2DTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setName(employee.getName());
        employeeDTO.setSkills(new HashSet<>(employee.getSkills()));
        employeeDTO.setDaysAvailable(new HashSet<>(employee.getDaysAvailable()));
        return employeeDTO;
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        employee.setDaysAvailable(new ArrayList<DayOfWeek>(daysAvailable));
        employeeService.saveEmployee(employee);

    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        List<Employee> employeesForService = employeeService.getAvailableEmployees(employeeDTO.getSkills(), employeeDTO.getDate().getDayOfWeek());
        return employeesForService.stream().map(this::Employee2DTO).collect(Collectors.toList());
    }

}

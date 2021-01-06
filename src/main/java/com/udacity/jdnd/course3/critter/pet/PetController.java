package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.model.Customer;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    PetService petService;
    @Autowired
    CustomerService customerService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {

        Customer customer = customerService.findById( petDTO.getOwnerId());

        Pet pet = new Pet();
        pet.setName( petDTO.getName());
        pet.setPetType(petDTO.getType());
        pet.setOwner(customer);
        pet.setBirthDate(petDTO.getBirthDate());
        pet.setNotes(petDTO.getNotes());

        if (petDTO.getId() != 0) {
            pet = petService.getPetById(petDTO.getId() );
        }

        Pet pet1 = petService.savePet(pet);
        customer.getPets().add(pet1);
        customerService.saveCustomer(customer);
        petDTO.setId(pet1.getId());
        return petDTO;


    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        throw new UnsupportedOperationException();
    }

    @GetMapping
    public List<PetDTO> getPets(){
        throw new UnsupportedOperationException();
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        throw new UnsupportedOperationException();
    }
}

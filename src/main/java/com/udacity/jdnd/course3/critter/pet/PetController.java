package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.model.Customer;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
        //customer.getPets().add(pet1); Fix by Mr Yuri the Master!!
        customerService.saveCustomer(customer);
        petDTO.setId(pet1.getId());
        return petDTO;




    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet pet = petService.getPetById(petId);
        return this.Pet2DTO(pet);
    }

    private PetDTO Pet2DTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        petDTO.setId(pet.getId());
        petDTO.setBirthDate(pet.getBirthDate());
        petDTO.setName(pet.getName());
        petDTO.setNotes(pet.getNotes());
        petDTO.setOwnerId(pet.getOwner().getId());
        petDTO.setType(pet.getPetType());
        return petDTO;
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<Pet> pets = petService.getAllPets();
        return pets.stream().map(this::Pet2DTO).collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
       List<Pet> pets = petService.getAllByOwnerId(ownerId);
       return pets.stream().map(this::Pet2DTO).collect(Collectors.toList());
    }
}

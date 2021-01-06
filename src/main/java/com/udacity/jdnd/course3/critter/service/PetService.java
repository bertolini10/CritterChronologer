package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.model.Customer;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class PetService implements IPetService {

    @Autowired
    PetRepository petRepository;
    @Autowired
    CustomerRepository customerRepository;

    @Override
    public Pet savePet(Pet pet) {
        petRepository.save(pet);
        Customer owner = pet.getOwner();

        if (owner != null) {
            long customerID = owner.getId();
            Optional<Customer> customerOptional = customerRepository.findById(customerID);
            if (customerOptional.isPresent()) {
                Customer customer = customerOptional.get();
                if(customer.getPets() != null) {
                    customer.getPets().add(pet);
                }
                customerRepository.save(customer);
            }
        } else {
           return  null;
        }

        return pet;
    }

    @Override
    public Pet getPetById(long id) {
        Pet pet = new Pet();
        Optional<Pet> optionalPet = petRepository.findById(id);
        if(optionalPet.isPresent()){
            pet = optionalPet.get();
        }else {
           return null;
        }
        return pet;
    }


    @Override
    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    @Override
    public List<Pet> getAllByOwnerId(long ownerId) {
        List<Pet> pets;
        Optional<Customer> customerOptional = customerRepository.findById(ownerId);
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            pets = customer.getPets();
        } else {
            pets = new ArrayList<>();
        }
        return pets;
    }

    public List<Pet> getAllPetsByIds(List<Long> ids){
        return petRepository.findAllById(ids);
    }
}
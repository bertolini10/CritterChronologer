package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.model.Pet;

import java.util.List;

public interface IPetService {

    Pet savePet(Pet pet);
    Pet getPetById(long id);
    List<Pet> getAllPets();
    List<Pet> getAllByOwnerId(long ownerId);
}

package bg.softuni.pethotel.service;

import bg.softuni.pethotel.model.binding.AnimalRegisterBindingModel;
import bg.softuni.pethotel.model.entity.AnimalEntity;
import bg.softuni.pethotel.model.enums.AnimalTypeEnum;

import java.io.IOException;
import java.util.Optional;

public interface AnimalService {

    void registerAnimal(AnimalRegisterBindingModel animal, String owner) throws IOException;

    AnimalTypeEnum findAnimalType(Long id);

    AnimalEntity findById(Long animalId);
}

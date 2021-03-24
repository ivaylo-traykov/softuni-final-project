package bg.softuni.pethotel.service;

import bg.softuni.pethotel.model.binding.AnimalRegisterBindingModel;
import bg.softuni.pethotel.model.entity.AnimalEntity;

import java.io.IOException;

public interface AnimalService {

    void registerAnimal(AnimalRegisterBindingModel animal, String owner) throws IOException;
}

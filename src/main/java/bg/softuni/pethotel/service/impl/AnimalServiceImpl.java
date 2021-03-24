package bg.softuni.pethotel.service.impl;

import bg.softuni.pethotel.model.binding.AnimalRegisterBindingModel;
import bg.softuni.pethotel.model.entity.AnimalEntity;
import bg.softuni.pethotel.model.entity.CatEntity;
import bg.softuni.pethotel.model.entity.DogEntity;
import bg.softuni.pethotel.repository.AnimalRepository;
import bg.softuni.pethotel.service.AnimalService;
import bg.softuni.pethotel.service.CloudinaryService;
import bg.softuni.pethotel.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class AnimalServiceImpl implements AnimalService {

    private final AnimalRepository animalRepository;
    private final CloudinaryService cloudinaryService;
    private final ModelMapper modelMapper;
    private final UserService userService;

    public AnimalServiceImpl(AnimalRepository animalRepository,
                             CloudinaryService cloudinaryService,
                             ModelMapper modelMapper,
                             UserService userService) {
        this.animalRepository = animalRepository;
        this.cloudinaryService = cloudinaryService;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @Override
    public void registerAnimal(AnimalRegisterBindingModel animal, String owner) throws IOException {

        AnimalEntity pet;

        switch (animal.getType()) {
            case DOG:
                pet = modelMapper.map(animal, DogEntity.class);
                pet.setImageUrl("/images/dog_placeholder.jpg");
                break;
            case CAT:
                pet = modelMapper.map(animal, CatEntity.class);
                pet.setImageUrl("/images/cat_placeholder.jpg");
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + animal.getType());
        }

        if (animal.getBreed().isEmpty()) {
            pet.setBreed("Няма");
        }

        if (!animal.getImage().isEmpty()) {
            MultipartFile img = animal.getImage();
            String imageUrl = cloudinaryService.uploadImage(img);

            pet.setImageUrl(imageUrl);
        }

        pet.setOwner(userService.findByEmail(owner));

        animalRepository.save(pet);
    }
}

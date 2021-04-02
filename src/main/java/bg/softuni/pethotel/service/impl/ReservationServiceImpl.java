package bg.softuni.pethotel.service.impl;

import bg.softuni.pethotel.model.binding.ReservationBindingModel;
import bg.softuni.pethotel.model.entity.*;
import bg.softuni.pethotel.repository.ReservationRepository;
import bg.softuni.pethotel.service.AnimalService;
import bg.softuni.pethotel.service.ReservationService;
import bg.softuni.pethotel.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final AnimalService animalService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public ReservationServiceImpl(ReservationRepository reservationRepository, AnimalService animalService, UserService userService, ModelMapper modelMapper) {
        this.reservationRepository = reservationRepository;
        this.animalService = animalService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void bookReservation(ReservationBindingModel reservationBindingModel, Long animalId, String ownerEmail) {
        AnimalEntity animal = animalService.findById(animalId);
        UserEntity owner = userService.findByEmail(ownerEmail);
        ReservationEntity reservation;

        switch (animal.getAnimalType().name()) {
            case "DOG":
                reservation = new DogReservationEntity()
                        .setWalkType(reservationBindingModel.getWalkType())
                        .setSuiteType(reservationBindingModel.getSuiteType());
                break;
            case "CAT":
                reservation = new CatReservationEntity()
                        .setOwnToilet(reservationBindingModel.isOwnToilet());
                break;

            default:
                throw new IllegalStateException("Unex pected value: " + animal.getAnimalType());
        }

        reservation.setStartDate(reservationBindingModel.getStartDate());
        reservation.setEndDate(reservationBindingModel.getEndDate());
        reservation.setOwnFood(reservationBindingModel.isOwnFood());
        reservation.setAnimal(animal);
        reservation.setOwner(owner);

        reservationRepository.save(reservation);
    }

    @Override
    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }
}

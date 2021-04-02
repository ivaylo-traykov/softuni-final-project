package bg.softuni.pethotel.service;

import bg.softuni.pethotel.model.binding.ReservationBindingModel;

public interface ReservationService {
    void bookReservation(ReservationBindingModel reservationBindingModel, Long animalId, String ownerEmail);

    void deleteReservation(Long id);
}

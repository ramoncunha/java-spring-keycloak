package com.ramon.myplayground.application.services;

import com.ramon.myplayground.application.presentation.CarController;
import com.ramon.myplayground.domain.models.CarEntity;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class HateoasLinkService {

    public void getOneLink(CarEntity carEntity) {
        linkTo(methodOn(CarController.class)
                .getOneCar(carEntity.getIdCar()))
                .withSelfRel();
    }

    public void getAllLink(CarEntity car) {
        linkTo(methodOn(CarController.class)
                .getAllCars())
                .withRel("Car List");
    }

    public void deleteLink(CarEntity car) {
        linkTo(methodOn(CarController.class)
                .deleteCar(car.getIdCar()))
                .withRel("Delete");
    }
}

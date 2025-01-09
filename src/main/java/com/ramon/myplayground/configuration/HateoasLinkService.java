package com.ramon.myplayground.configuration;

import com.ramon.myplayground.car.CarController;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class HateoasLinkService {

    public Link getOneLink(UUID id) {
        return linkTo(methodOn(CarController.class)
                .getOneCar(id))
                .withSelfRel();
    }

    public Link getAllLink() {
        return linkTo(methodOn(CarController.class)
                .getAllCars())
                .withRel("List");
    }

    public Link deleteLink(UUID id) {
        return linkTo(methodOn(CarController.class)
                .deleteCar(id))
                .withRel("Delete");
    }
}

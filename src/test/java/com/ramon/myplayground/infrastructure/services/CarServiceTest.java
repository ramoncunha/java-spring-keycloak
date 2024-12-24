package com.ramon.myplayground.infrastructure.services;

import com.ramon.myplayground.domain.exceptions.CarNotFoundException;
import com.ramon.myplayground.infrastructure.mappers.CarEntityMapper;
import com.ramon.myplayground.infrastructure.models.CarRequest;
import com.ramon.myplayground.infrastructure.repositories.CarRepository;
import com.ramon.myplayground.infrastructure.repositories.models.CarEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    @Mock
    private CarRepository carRepository;
    @Mock
    private CarEntityMapper carEntityMapper;
    @InjectMocks
    private CarService unit;

    @Test
    void save_givenCarRequest_shouldMapAndSaveCar() {
        CarRequest carRequest = Mockito.mock(CarRequest.class);
        CarEntity carEntity = Mockito.mock(CarEntity.class);

        Mockito.when(carEntityMapper.map(carRequest)).thenReturn(carEntity);
        Mockito.when(carRepository.save(carEntity)).thenReturn(carEntity);

        CarEntity actual = unit.save(carRequest);

        Assertions.assertThat(actual).isEqualTo(carEntity);
    }

    @Test
    void findAll_shouldReturnAllCars() {
        var cars = List.of(Mockito.mock(CarEntity.class));
        Mockito.when(carRepository.findAll()).thenReturn(cars);
        List<CarEntity> actual = unit.findAll();

        Assertions.assertThat(actual).isEqualTo(cars);
    }

    @Test
    void findById_givenId_shouldReturnCar() {
        var car = Mockito.mock(CarEntity.class);
        var id = UUID.fromString("8224f0a2-7df0-4ed1-81ce-11a5f0839e8f");
        Mockito.when(carRepository.findById(id)).thenReturn(Optional.of(car));

        CarEntity actual = unit.findById(id);

        Assertions.assertThat(actual).isEqualTo(car);
    }

    @Test
    void findById_givenIdUnavailable_shouldThrowException() {
        var id = UUID.fromString("8224f0a2-7df0-4ed1-81ce-11a5f0839e8f");
        Mockito.when(carRepository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> unit.findById(id))
                .isInstanceOf(CarNotFoundException.class);
    }

    @Test
    void delete_givenId_shouldDeleteCar() {
        var id = UUID.fromString("8224f0a2-7df0-4ed1-81ce-11a5f0839e8f");
        unit.delete(id);
        Mockito.verify(carRepository, Mockito.times(1)).deleteById(id);
    }
}

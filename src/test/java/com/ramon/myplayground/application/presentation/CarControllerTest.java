package com.ramon.myplayground.application.presentation;

import com.ramon.myplayground.application.services.HateoasLinkService;
import com.ramon.myplayground.application.services.ICarService;
import com.ramon.myplayground.domain.models.CarEntity;
import com.ramon.myplayground.infrastructure.dtos.CarRequest;
import com.ramon.myplayground.infrastructure.dtos.CarResponse;
import com.ramon.myplayground.infrastructure.mappers.CarResponseMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class CarControllerTest {

    @Mock
    private ICarService carService;
    @Mock
    private HateoasLinkService hateoasLinkService;
    @Mock
    private CarResponseMapper carResponseMapper;
    @InjectMocks
    private CarController unit;

    @Test
    void saveCar_givenValidRequest_shouldSaveAndReturnCar() {
        var request = Mockito.mock(CarRequest.class);
        var entity = Mockito.mock(CarEntity.class);
        var expected = Mockito.mock(CarResponse.class);

        Mockito.when(carService.save(request)).thenReturn(entity);
        Mockito.when(carResponseMapper.fromCarEntity(entity)).thenReturn(expected);

        ResponseEntity<CarResponse> actual = unit.saveCar(request);

        Assertions.assertThat(actual.getBody()).isEqualTo(expected);
        Assertions.assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void getAllCars_shouldReturnAllCars() {
        var entity = Mockito.mock(CarEntity.class);
        var entities = Collections.singletonList(entity);
        var response = Mockito.mock(CarResponse.class);
        var hateoasLink = Mockito.mock(Link.class);
        var UUIDMock = Mockito.mock(UUID.class);

        Mockito.when(carService.findAll()).thenReturn(entities);
        Mockito.when(carResponseMapper.fromCarEntity(entity)).thenReturn(response);
        Mockito.when(response.getId()).thenReturn(UUIDMock);
        Mockito.when(hateoasLinkService.getOneLink(UUIDMock)).thenReturn(hateoasLink);

        ResponseEntity<List<CarResponse>> actual = unit.getAllCars();

        List<CarResponse> expected = Collections.singletonList(response);

        Assertions.assertThat(actual.getBody()).isEqualTo(expected);
        Assertions.assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
        Mockito.verify(hateoasLinkService, Mockito.times(1)).getOneLink(UUIDMock);
    }

    @Test
    void getOneCar_givenValidId_shouldReturnCar() {
        var entity = Mockito.mock(CarEntity.class);
        var response = Mockito.mock(CarResponse.class);
        var hateoasLink = Mockito.mock(Link.class);
        var UUIDMock = Mockito.mock(UUID.class);

        Mockito.when(carService.findById(UUIDMock)).thenReturn(entity);
        Mockito.when(carResponseMapper.fromCarEntity(entity)).thenReturn(response);
        Mockito.when(response.getId()).thenReturn(UUIDMock);
        Mockito.when(hateoasLinkService.deleteLink(UUIDMock)).thenReturn(hateoasLink);

        ResponseEntity<CarResponse> actual = unit.getOneCar(UUIDMock);

        Assertions.assertThat(actual.getBody()).isEqualTo(response);
        Assertions.assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
        Mockito.verify(hateoasLinkService, Mockito.times(1)).getAllLink();
        Mockito.verify(hateoasLinkService, Mockito.times(1)).deleteLink(UUIDMock);
    }

    @Test
    void updateCar_givenValidIdAndRequest_shouldUpdateCar() {
        var entity = Mockito.mock(CarEntity.class);
        var request = Mockito.mock(CarRequest.class);
        var response = Mockito.mock(CarResponse.class);
        var UUIDMock = Mockito.mock(UUID.class);

        Mockito.when(carService.update(UUIDMock, request)).thenReturn(entity);
        Mockito.when(carResponseMapper.fromCarEntity(entity)).thenReturn(response);

        ResponseEntity<CarResponse> actual = unit.updateCar(UUIDMock, request);

        Assertions.assertThat(actual.getBody()).isEqualTo(response);
        Assertions.assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void deleteCar_givenValidId_shouldDeleteCar() {
        var UUIDMock = Mockito.mock(UUID.class);

        unit.deleteCar(UUIDMock);

        Mockito.verify(carService, Mockito.times(1)).delete(UUIDMock);
    }
}

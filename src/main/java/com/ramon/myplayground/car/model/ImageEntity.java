package com.ramon.myplayground.car.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "image")
public class ImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String description;
    private byte[] picByte;
}

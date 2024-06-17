package com.gt.findingcenter.entity.map;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="map_info")
public class Map {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    private String address;
    private String lat;
    private String lng;

}

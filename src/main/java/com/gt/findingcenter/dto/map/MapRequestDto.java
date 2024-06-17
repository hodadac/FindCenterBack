package com.gt.findingcenter.dto.map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
public class MapRequestDto {
    private String address;
    private String lat;
    private String lng;

}

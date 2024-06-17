package com.gt.findingcenter.dto.map;


import com.gt.findingcenter.entity.map.Map;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class MapResponseDto{
    private String latitude;
    private String longitude;

    @Builder
    public MapResponseDto(String X, String Y){
        this.latitude = X;
        this.longitude = Y;
    }
}

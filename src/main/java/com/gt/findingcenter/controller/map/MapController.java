package com.gt.findingcenter.controller.map;

import com.gt.findingcenter.dto.map.MapRequestDto;
import com.gt.findingcenter.dto.map.MapResponseDto;
import com.gt.findingcenter.entity.hull.Hull;
import com.gt.findingcenter.service.map.MapService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/map")
@CrossOrigin(origins = "*")
public class MapController {
    private final MapService mapService;
    private final ArrayList<Hull> list;
    @PostMapping("/findcenter")
    public ResponseEntity<?> findcenter(@RequestBody List<MapRequestDto> dtoList){

       MapResponseDto dd = mapService.findCenter(dtoList);

       return ResponseEntity.status(HttpStatus.OK).body(dd);
    }


}

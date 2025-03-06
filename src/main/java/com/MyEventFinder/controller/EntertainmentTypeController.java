package com.MyEventFinder.controller;

import com.MyEventFinder.model.DTO.EntertainmentDTO;
import com.MyEventFinder.model.DTO.EntertainmentTypeDTO;
import com.MyEventFinder.service.EntertainmentService;
import com.MyEventFinder.service.EntertainmentTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/entertainmentType")
@RequiredArgsConstructor
public class EntertainmentTypeController {
    private final EntertainmentTypeService entTypeService;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public ResponseEntity<?> getAllEntertainmentTypes() {
        return entTypeService.findAll();
    }
    @RequestMapping(path = "/", method = RequestMethod.POST)
    public ResponseEntity<?> createEntType(@RequestBody EntertainmentTypeDTO dto) {
        return entTypeService.createEntType(dto);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getEntTypeById(@PathVariable Long id) {
        return entTypeService.getEntTypeById(id);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateEntType(@PathVariable Long id, @RequestBody EntertainmentTypeDTO dto) {
        return entTypeService.updateEntType(id, dto);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteEntType(@PathVariable Long id) {
        return entTypeService.deleteEntType(id);
    }
}

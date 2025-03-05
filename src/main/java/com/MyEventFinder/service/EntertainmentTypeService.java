package com.MyEventFinder.service;

import com.MyEventFinder.repository.EntertainmentTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EntertainmentTypeService {
    private final EntertainmentTypeRepository entTypeRepo;

    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(entTypeRepo.findAll());
    }
}

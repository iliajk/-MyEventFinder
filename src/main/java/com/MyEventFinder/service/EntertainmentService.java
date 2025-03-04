package com.MyEventFinder.service;

import com.MyEventFinder.model.DTO.EntertainmentDTO;
import com.MyEventFinder.model.entity.Entertainment;
import com.MyEventFinder.repository.EntertainmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EntertainmentService {
    private final EntertainmentRepository entRepo;

    public ResponseEntity<?> getAllEntertainments() {
        List<Entertainment> ents = entRepo.getAllByDeleted(false);
        return ResponseEntity.ok().body(ents);
    }

    public ResponseEntity<?> createEntertainment(EntertainmentDTO entDTO) {
        Entertainment newEnt;
        if (entDTO == null) {
            return ResponseEntity.badRequest().body("Entertainment cannot be null!");
        }
        try {
            newEnt = EntertainmentDTO.toNewEntertainment(entDTO);
        } catch (RuntimeException runtimeException) {
            return ResponseEntity.badRequest().body(runtimeException.getMessage());
        }
        newEnt = entRepo.save(newEnt);
        entDTO = EntertainmentDTO.toDTO(newEnt);
        return ResponseEntity.status(HttpStatus.CREATED).body(entDTO);
    }

    public ResponseEntity<?> getEntertainmentById(Long id) {
        Entertainment entertainment = entRepo.findById(id).orElse(null);
        if (entertainment != null) {
            return ResponseEntity.ok().body(EntertainmentDTO.toDTO(entertainment));
        }
        return ResponseEntity.badRequest().body("Entertainment not found");
    }

    public ResponseEntity<?> updateEntertainment(Long id, EntertainmentDTO dto) {
        Entertainment existingEnt = entRepo.findById(id).orElse(null);
        if (existingEnt != null && dto != null) {
            if (dto.getTitle() != null && !dto.getTitle().isEmpty()) {
                existingEnt.setTitle(dto.getTitle());
            }
            if (dto.getDescription() != null && !dto.getDescription().isEmpty()) {
                existingEnt.setDescription(dto.getDescription());
            }
            try {
                if (dto.getDate() != null && !dto.getDate().isEmpty()) {
                    existingEnt.setDate(LocalDateTime.parse(dto.getDate()));
                }
                if (dto.getPrice() != null && !dto.getPrice().isEmpty()) {
                    existingEnt.setPrice(BigDecimal.valueOf(Double.parseDouble(dto.getPrice())));
                }
                if (dto.getCurrency() != null && !dto.getCurrency().isEmpty()) {
                    existingEnt.setCurrency(Currency.getInstance(dto.getCurrency()));
                }
            } catch (RuntimeException runtimeException) {
                System.out.println(runtimeException.getMessage());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(runtimeException.getMessage());
            }
            existingEnt = entRepo.save(existingEnt);
            return ResponseEntity.ok().body(EntertainmentDTO.toDTO(existingEnt));
        } else {
            return ResponseEntity.badRequest().body("Entertainment not found");
        }
    }

    public ResponseEntity<?> deleteEntertainment(Long id) {
        Entertainment existingEnt = entRepo.findById(id).orElse(null);
        if (existingEnt != null) {
            existingEnt.setDeleted(true);
            existingEnt.setDeleted_at(LocalDateTime.now());
            existingEnt = entRepo.save(existingEnt);
            return ResponseEntity.ok().body("Entertainment with id = " + id + " was deleted");
        }
        return ResponseEntity.badRequest().body("Entertainment not found");
    }
}

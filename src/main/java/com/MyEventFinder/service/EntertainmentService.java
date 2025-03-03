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
import java.time.format.DateTimeParseException;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EntertainmentService {
    private final EntertainmentRepository entRepo;

    public ResponseEntity<?> getAllEntertainments() {
        List<Entertainment> ents = entRepo.findAll();
        return ResponseEntity.ok().body(ents);
    }

    public ResponseEntity<?> createEntertainment(EntertainmentDTO ent) {
        try {
            Entertainment newEnt = Entertainment.builder()
                    .title(ent.getTitle())
                    .description(ent.getDescription())
                    .date(LocalDateTime.parse(ent.getDate()))
                    .price(BigDecimal.valueOf(Long.parseLong(ent.getPrice())))
                    .currency(Currency.getInstance(ent.getCurrency()))
                    .build();
            Entertainment entertainment = entRepo.save(newEnt);
            newEnt.setId(entertainment.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(newEnt);
        } catch (RuntimeException runtimeException) {
            System.out.println(runtimeException.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(runtimeException.getMessage());
        }
    }

    public ResponseEntity<?> getEntertainmentById(Long id) {
        Entertainment entertainment = entRepo.findById(id).orElse(null);
        if (entertainment != null) {
            return ResponseEntity.ok().body(EntertainmentDTO.builder()
                    .id(entertainment.getId())
                    .title(entertainment.getTitle())
                    .description(entertainment.getDescription())
                    .price(dto.getPrice())
                    .currency(dto.getCurrency())
                    .date(dto.getDate())
                    .build());
        }
        return ResponseEntity.ok().body(entRepo.findById(id));
    }

    public ResponseEntity<?> updateEntertainment(Long id, EntertainmentDTO dto) {
        Entertainment existingEnt = entRepo.findById(id).orElse(null);
        if (existingEnt != null) {
            if (dto.getTitle() != null) {
                existingEnt.setTitle(dto.getTitle());
            }
            if (dto.getDescription() != null) {
                existingEnt.setDescription(dto.getDescription());
            }
            try {
                if (dto.getDate() != null) {
                    existingEnt.setDate(LocalDateTime.parse(dto.getDate()));
                }
                if (dto.getPrice() != null) {
                    existingEnt.setPrice(BigDecimal.valueOf(Long.parseLong(dto.getPrice())));
                }
                if (dto.getCurrency() != null) {
                    existingEnt.setCurrency(Currency.getInstance(dto.getCurrency()));
                }
            } catch (RuntimeException runtimeException) {
                System.out.println(runtimeException.getMessage());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(runtimeException.getMessage());
            }
            existingEnt = entRepo.save(existingEnt);
            return ResponseEntity.ok().body(EntertainmentDTO.builder()
                    .id(existingEnt.getId())
                    .title(existingEnt.getTitle())
                    .description(existingEnt.getDescription())
                    .price(dto.getPrice())
                    .currency(dto.getCurrency())
                    .date(dto.getDate())
                    .build());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> deleteEntertainment(Long id) {
        Entertainment existingEnt = entRepo.findById(id).orElse(null);
        if(existingEnt != null) {
            existingEnt.setDeleted(true);
            existingEnt.setDeleted_at(LocalDateTime.now());
            existingEnt = entRepo.save(existingEnt);
        }
    }
}

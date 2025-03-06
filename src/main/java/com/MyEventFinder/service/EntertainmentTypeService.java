package com.MyEventFinder.service;

import com.MyEventFinder.model.DTO.EntertainmentTypeDTO;
import com.MyEventFinder.model.entity.Entertainment;
import com.MyEventFinder.model.entity.EntertainmentType;
import com.MyEventFinder.repository.EntertainmentTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EntertainmentTypeService {
    private final EntertainmentTypeRepository entTypeRepo;

    public ResponseEntity<?> findAll() {
        List<EntertainmentType> entTypes = entTypeRepo.getAllByDeleted(false);
        List<EntertainmentTypeDTO> entTypesDTO = new ArrayList<>(entTypes.stream().map(this::toDTO).toList());
        return ResponseEntity.ok().body(entTypesDTO);
    }

    public ResponseEntity<?> createEntType(EntertainmentTypeDTO dto) {
        if (dto.getTitle() != null && !dto.getTitle().isEmpty()) {
            EntertainmentType entType = EntertainmentType.builder()
                    .title(dto.getTitle())
                    .description(dto.getDescription())
                    .build();
            entType = entTypeRepo.save(entType);
            return ResponseEntity.ok(toDTO(entType));
        }
        return ResponseEntity.badRequest().body("Wrong Entertainment Type name");
    }

    public ResponseEntity<?> getEntTypeById(Long id) {
        return ResponseEntity.ok(toDTO(entTypeRepo.findById(id).orElse(null)));
    }

    public ResponseEntity<?> updateEntType(Long id, EntertainmentTypeDTO dto) {
        EntertainmentType type = entTypeRepo.findById(id).orElse(null);
        if (type != null && !type.getDeleted()) {
            if (dto.getTitle() != null && !dto.getTitle().isEmpty()) {
                type.setTitle(dto.getTitle());
            }
            if (dto.getDescription() != null && !dto.getDescription().isEmpty()) {
                type.setDescription(dto.getDescription());
            }
            entTypeRepo.save(type);
            return ResponseEntity.ok().body(toDTO(type));
        }
        return ResponseEntity.badRequest().body("Entertainment Type not found");
    }

    public ResponseEntity<?> deleteEntType(Long id) {
        EntertainmentType existingEntType = entTypeRepo.findById(id).orElse(null);
        if (existingEntType != null && !existingEntType.getDeleted()) {
            existingEntType.setDeleted(true);
            existingEntType.setDeleted_at(LocalDateTime.now());
            entTypeRepo.save(existingEntType);
            return ResponseEntity.ok().body("Entertainment with id = " + id + " was deleted");
        }
        return ResponseEntity.badRequest().body("Entertainment type not found or already deleted");
    }

    // transfer from EntertainmentType to Dto
    private EntertainmentTypeDTO toDTO(EntertainmentType entity) {
        if (entity != null) {
            return EntertainmentTypeDTO.builder()
                    .id(entity.getId())
                    .title(entity.getTitle())
                    .description(entity.getDescription())
                    .build();
        }
        return null;
    }

    // create entertainment from DTO
    private EntertainmentType createNewEntertainmentType(EntertainmentTypeDTO dto) throws RuntimeException {
        if (dto != null) {
            try {
                return EntertainmentType.builder()
                        .title(dto.getTitle())
                        .description(dto.getDescription())
                        .build();
            } catch (RuntimeException runtimeException) {
                System.out.println(runtimeException.getMessage());
                throw runtimeException;
            }
        }
        return null;
    }


}

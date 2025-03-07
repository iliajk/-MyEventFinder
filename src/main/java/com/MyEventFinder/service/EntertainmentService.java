package com.MyEventFinder.service;

import com.MyEventFinder.model.DTO.EntertainmentDTO;
import com.MyEventFinder.model.entity.Entertainment;
import com.MyEventFinder.model.entity.EntertainmentType;
import com.MyEventFinder.model.entity.Location;
import com.MyEventFinder.model.entity.User;
import com.MyEventFinder.model.specifications.EntertainmentSpecification;
import com.MyEventFinder.repository.EntertainmentRepository;
import com.MyEventFinder.repository.EntertainmentTypeRepository;
import com.MyEventFinder.repository.LocationRepository;
import com.MyEventFinder.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class EntertainmentService {
    private final EntertainmentRepository entRepo;
    private final EntertainmentTypeRepository entTypeRepo;
    private final LocationRepository locationRepo;
    private final UserRepository userRepo;

    public ResponseEntity<?> getAllEntertainments(Long typeId, String location, Long maxPrice) {
        BigDecimal maxPriceBigDecimal = null;
        if(maxPrice != null){
             maxPriceBigDecimal = new BigDecimal(maxPrice);
        }
        Specification<Entertainment> spec = Specification
                .where(EntertainmentSpecification.hasType(typeId))
                .and(EntertainmentSpecification.hasLocation(location))
                .and(EntertainmentSpecification.hasPriceLessThenOrEqual(maxPriceBigDecimal))
                .and(EntertainmentSpecification.hasDeleted(false));
        List<EntertainmentDTO> dtos = entRepo.findAll(spec).stream().map(this::toDTO).toList();
        return ResponseEntity.ok().body(dtos);
    }

    public ResponseEntity<?> createEntertainment(EntertainmentDTO entDTO) {
        Entertainment newEnt;
        try {
            newEnt = createNewEntertainment(entDTO);
        } catch (RuntimeException runtimeException) {
            return ResponseEntity.badRequest().body(runtimeException.getMessage());
        }
        newEnt = entRepo.save(newEnt);
        entDTO = toDTO(newEnt);
        return ResponseEntity.status(HttpStatus.CREATED).body(entDTO);
    }

    public ResponseEntity<?> getEntertainmentById(Long id) {
        Entertainment entertainment = entRepo.findById(id).orElse(null);
        if (entertainment != null) {
            return ResponseEntity.ok().body(toDTO(entertainment));
        }
        return ResponseEntity.badRequest().body("Entertainment not found");
    }

    public ResponseEntity<?> updateEntertainment(Long id, EntertainmentDTO dto) {
        Entertainment existingEnt = entRepo.findById(id).orElse(null);
        if (existingEnt != null && dto != null && !existingEnt.getDeleted()) {
            EntertainmentType type = entTypeRepo.findById(dto.getTypeId()).orElse(null);
            List<Entertainment> allEnt = new ArrayList<>();
            if (type != null) {
                existingEnt.setType(type);
                allEnt = type.getEntertainments();
            }
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
            allEnt.add(existingEnt);
            type.setEntertainments(allEnt);
            entTypeRepo.save(type);
            return ResponseEntity.ok().body(toDTO(existingEnt));
        } else {
            return ResponseEntity.badRequest().body("Entertainment not found");
        }
    }

    public ResponseEntity<?> deleteEntertainment(Long id) {
        Entertainment existingEnt = entRepo.findById(id).orElse(null);
        if (existingEnt != null && !existingEnt.getDeleted()) {
            existingEnt.setDeleted(true);
            existingEnt.setDeleted_at(LocalDateTime.now());
            entRepo.save(existingEnt);
            return ResponseEntity.ok().body("Entertainment with id = " + id + " was deleted");
        }
        return ResponseEntity.badRequest().body("Entertainment not found or already deleted");
    }

    // transfer from Entertainment to Dto
    private EntertainmentDTO toDTO(Entertainment entity) {
        if (entity != null) {
            return EntertainmentDTO.builder()
                    .id(entity.getId())
                    .title(entity.getTitle())
                    .description(entity.getDescription())
                    .price(entity.getPrice().toString())
                    .currency(entity.getCurrency().toString())
                    .date(entity.getDate().toString())
                    .typeId(entity.getType().getId())
                    .locationId(entity.getLocation().getId())
                    .build();
        }
        return null;
    }

    // create entertainment from DTO
    private Entertainment createNewEntertainment(EntertainmentDTO dto) throws RuntimeException {
        if (dto != null) {
            EntertainmentType type = null;
            Location location = null;
            List<User> executors = null;
            User customer = null;

            Long typeId = dto.getTypeId();
            Long locationId = dto.getLocationId();
            Long customerId = dto.getCustomerId();
            Long[] executorIds = dto.getExecutorIds();

            if (typeId != null) {
                type = entTypeRepo.findById(typeId).orElse(null);
            }
            if (locationId != null) {
                location = locationRepo.findById(locationId).orElse(null);
            }
            if (customerId != null) {
                customer = userRepo.findById(customerId).orElse(null);
            }
            if (executorIds != null) {
                executors = new ArrayList<>(userRepo.findAllById(Stream.of(executorIds).toList()));
            }
            try {
                return Entertainment.builder()
                        .title(dto.getTitle())
                        .description(dto.getDescription())
                        .date(LocalDateTime.parse(dto.getDate()))
                        .price(BigDecimal.valueOf(Double.parseDouble(dto.getPrice())))
                        .currency(Currency.getInstance(dto.getCurrency()))
                        .type(type)
                        .location(location)
                        .customer(customer)
                        .executors(executors)
                        .build();
            } catch (RuntimeException runtimeException) {
                System.out.println(runtimeException.getMessage());
                throw runtimeException;
            }
        }
        return null;
    }
}

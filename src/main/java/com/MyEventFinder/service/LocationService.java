package com.MyEventFinder.service;

import com.MyEventFinder.model.DTO.EntertainmentDTO;
import com.MyEventFinder.model.DTO.LocationDTO;
import com.MyEventFinder.model.entity.Entertainment;
import com.MyEventFinder.model.entity.EntertainmentType;
import com.MyEventFinder.model.entity.Location;
import com.MyEventFinder.model.entity.User;
import com.MyEventFinder.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
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
public class LocationService {
    private final LocationRepository locationRepository;

    public ResponseEntity<?> createLocation(LocationDTO locationDTO) {
        Location location;
        if (locationDTO != null) {
            try {
                location = createNewLocation(locationDTO);
            } catch (RuntimeException runtimeException) {
                System.out.println(runtimeException.getMessage());
                return ResponseEntity.badRequest().body(runtimeException.getMessage());
            }
            locationRepository.save(location);
            locationDTO.setId(location.getId());
            return ResponseEntity.ok(locationDTO);
        }
        return ResponseEntity.badRequest().body("DTO could not be empty");
    }



    public ResponseEntity<?> getLocationById(Long id) {
        Location location = locationRepository.findById(id).orElse(null);
        if(location != null) {
            return ResponseEntity.ok(toDTO(location));
        }
        return ResponseEntity.badRequest().body("Location not found");
    }

    public ResponseEntity<?> updateLocation(Long id, LocationDTO dto) {
        Location location = locationRepository.findById(id).orElse(null);
        if (location != null) {
            if(dto.getName() != null) {
                location.setName(dto.getName());
            }
            if(dto.getAddress() != null) {
                location.setAddress(dto.getAddress());
            }
            if(dto.getCity() != null) {
                location.setCity(dto.getCity());
            }
            if(dto.getState() != null) {
                location.setState(dto.getState());
            }
            if(dto.getPhone() != null) {
                location.setPhone(dto.getPhone());
            }
            if(dto.getEmail() != null) {
                location.setEmail(dto.getEmail());
            }
            if(dto.getMapLink() != null) {
                location.setMapLink(dto.getMapLink());
            }
            if(dto.getCountry() != null) {
                location.setCountry(dto.getCountry());
            }
            if(dto.getCapacity() != null) {
                location.setCapacity(dto.getCapacity());
            }
            return ResponseEntity.ok().body(toDTO(locationRepository.save(location)));
        }
        return ResponseEntity.badRequest().body("Location not found");
    }

    public ResponseEntity<?> deleteLocation(Long id) {
        Location location = locationRepository.findById(id).orElse(null);
        if(location != null) {
            location.setDeleted(true);
            location.setDeleted_at(LocalDateTime.now());
            locationRepository.save(location);
            return ResponseEntity.ok().body("Location successfully deleted");
        }
        return ResponseEntity.badRequest().body("Location not found");
    }

    // transfer from Entertainment to Dto
    private LocationDTO toDTO(Location entity) {
        if (entity != null) {
            return LocationDTO.builder()
                    .id(entity.getId())
                    .name(entity.getName())
                    .address(entity.getAddress())
                    .city(entity.getCity())
                    .state(entity.getState())
                    .phone(entity.getPhone())
                    .email(entity.getEmail())
                    .mapLink(entity.getMapLink())
                    .country(entity.getCountry())
                    .capacity(entity.getCapacity())
                    .build();
        }
        return null;
    }

    // create entertainment from DTO
    private Location createNewLocation(LocationDTO dto) throws RuntimeException {
        try {
            return Location.builder()
                    .name(dto.getName())
                    .address(dto.getAddress())
                    .city(dto.getCity())
                    .state(dto.getState())
                    .country(dto.getCountry())
                    .phone(dto.getPhone())
                    .email(dto.getEmail())
                    .mapLink(dto.getMapLink())
                    .capacity(dto.getCapacity())
                    .build();
        } catch (RuntimeException runtimeException) {
            System.out.println(runtimeException.getMessage());
            throw runtimeException;
        }
    }
}

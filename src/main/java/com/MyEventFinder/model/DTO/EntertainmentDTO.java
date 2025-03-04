package com.MyEventFinder.model.DTO;

import com.MyEventFinder.model.entity.Entertainment;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EntertainmentDTO {
    private Long id;
    private String title;
    private String description;
    private String price;
    private String currency;
    private String date;

    public static EntertainmentDTO toDTO(Entertainment entity) {
        if (entity != null) {
            return EntertainmentDTO.builder()
                    .id(entity.getId())
                    .title(entity.getTitle())
                    .description(entity.getDescription())
                    .price(entity.getPrice().toString())
                    .currency(entity.getCurrency().toString())
                    .date(entity.getDate().toString())
                    .build();
        }
        return null;
    }

    public static Entertainment toNewEntertainment(EntertainmentDTO dto) throws RuntimeException {
        if(dto != null) {
            try {
                return Entertainment.builder()
                        .title(dto.getTitle())
                        .description(dto.getDescription())
                        .date(LocalDateTime.parse(dto.getDate()))
                        .price(BigDecimal.valueOf(Double.parseDouble(dto.getPrice())))
                        .currency(Currency.getInstance(dto.getCurrency()))
                        .build();
            } catch (RuntimeException runtimeException) {
                System.out.println(runtimeException.getMessage());
                throw runtimeException;
            }
        }
        return null;
    }
}

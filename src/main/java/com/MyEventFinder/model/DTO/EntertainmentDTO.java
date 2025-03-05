package com.MyEventFinder.model.DTO;

import lombok.*;

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
    private Long typeId;
    private Long locationId;
    private Long customerId;
    private Long[] executorIds;
}

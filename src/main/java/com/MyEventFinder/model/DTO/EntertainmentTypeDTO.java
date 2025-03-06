package com.MyEventFinder.model.DTO;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EntertainmentTypeDTO {
    private Long id;
    private String title;
    private String description;
}

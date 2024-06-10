package br.com.project.x.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    private String city;
    private String state;
    private String lat;
    private String lng;

}

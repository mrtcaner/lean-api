package com.assignment.api.model.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CarSearchResultDTO {

    private Integer id;

    private Double latitude;

    private Double longitude;

    private String maker;

    private String model;

    private Integer year;

    private Double distance;

}

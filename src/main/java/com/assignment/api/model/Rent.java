package com.assignment.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NamedQueries({
        @NamedQuery(name="Rent.findByUserIdAndCarIdAndStartDateIsNotNullAndEndDateIsNull",
                query="from Rent rent where rent.user.id= :userId and rent.car.id = :carId " +
                        "and rent.startDate is not null and rent.endDate is null"),
        @NamedQuery(name="Rent.findByIdAndUserIdAndStartDateIsNotNullAndEndDateIsNull",
                query="from Rent rent where rent.id = :id and rent.user.id= :userId and " +
                        "rent.startDate is not null and rent.endDate is null"),
        @NamedQuery(name="Rent.findByUserId",
                query="from Rent rent where rent.user.id= :userId"),
        @NamedQuery(name="Rent.findByRentIdUserId",
                query="from Rent rent where rent.user.id= :userId and rent.id = :id"),
})

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Rent {

    @Id
    @GeneratedValue
    private Integer id;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    private Car car;

    @Column
    private Double cost;

    @Column
    private LocalDateTime startDate;

    @Column
    private LocalDateTime endDate;

}

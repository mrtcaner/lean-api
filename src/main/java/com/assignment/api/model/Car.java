package com.assignment.api.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.assignment.api.model.dto.CarSearchResultDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@NamedNativeQueries({
        @NamedNativeQuery(
                name = "Car.findAvailableCarsWithinDefaultDistance",
                query = "SELECT car1.*,available.distance \n" +
                        "from Car car1  \n" +
                        "inner join (  \n" +
                        "\tSELECT  car.id, (   \n" +
                        "\t\t111.111 * \n" +
                        "\t\tDEGREES(ACOS(LEAST(1.0, COS(RADIANS(:latitude)) \n" +
                        "\t\t\t * COS(RADIANS(car.latitude)) \n" +
                        "\t\t\t * COS(RADIANS(:longitude - car.longitude)) \n" +
                        "\t\t\t + SIN(RADIANS(:latitude)) \n" +
                        "\t\t\t * SIN(RADIANS(car.latitude)))))  \n" +
                        "\t\t\t ) AS distance  \n" +
                        "\tFROM Car car  \n" +
                        "\tWHERE car.user_id is null \n" +
                        "\tGROUP BY car.id           \n" +
                        "\tHAVING distance < :diameter \n" +
                        "\tORDER BY distance  \n" +
                        ") as available on car1.id = available.id ",
                resultSetMapping = "CarSearchResultMapping"
        )
})

@SqlResultSetMapping(name = "CarSearchResultMapping",
        classes = {
                @ConstructorResult(
                        columns = {
                                @ColumnResult(name = "id", type = Integer.class),
                                @ColumnResult(name = "latitude", type = Double.class),
                                @ColumnResult(name = "longitude", type = Double.class),
                                @ColumnResult(name = "maker", type = String.class),
                                @ColumnResult(name = "model", type = String.class),
                                @ColumnResult(name = "year", type = Integer.class),
                                @ColumnResult(name = "distance", type = Double.class)},
                        targetClass = CarSearchResultDTO.class)
        })
@NamedQueries({
        @NamedQuery(name="Car.findByIdAndUserIdIsNull",
                query="from Car car where car.id= :id and car.user.id is null"),
        @NamedQuery(name="Car.findByIdAndUserId",
                query="from Car car where car.id= :id and car.user.id = :userId"),
        @NamedQuery(name="Car.findByUserId",
                query="from Car car where car.user.id = :userId"),
})
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Car {

    @Id
    @GeneratedValue
    private Integer id;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private String maker;

    @Column
    private String model;

    @Column
    private Integer year;

    @Column
    private Double latitude;

    @Column
    private Double longitude;

    @Version
    private Integer version;

}

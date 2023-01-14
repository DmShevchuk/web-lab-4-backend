package ru.weblab4.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "points")
@Getter
@Setter
@NoArgsConstructor
public class Point{
    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "x")
    private Float x;

    @Column(name = "y")
    private Float y;

    @Column(name = "r")
    private Float r;

    @Column(name = "is_hit")
    private Boolean isHit;

    @Column(name = "time_point")
    private Long timePoint;

    @Column(name = "execution_time")
    private Long executionTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}

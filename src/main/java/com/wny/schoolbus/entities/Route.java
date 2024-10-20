package com.wny.schoolbus.entities;

import com.wny.schoolbus.entities.impl.SchoolBusImpl;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "routes")
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;
    private LocalDateTime departTime;
    private LocalDateTime returnedTime;
    private String remarks;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "routes_buses",
            joinColumns = {@JoinColumn(name = "route_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "bus_id", referencedColumnName = "id")}
    )
    private List<SchoolBusImpl> buses;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "routes_drivers",
            joinColumns = {@JoinColumn(name = "route_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "driver_id", referencedColumnName = "id")}
    )
    private List<Employee> drivers;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "routes_monitors",
            joinColumns = {@JoinColumn(name = "route_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "monitor_id", referencedColumnName = "id")}
    )
    private List<Employee> monitors;
}

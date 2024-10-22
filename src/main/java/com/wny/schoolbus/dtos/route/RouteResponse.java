package com.wny.schoolbus.dtos.route;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RouteResponse {

    private String id;
    private String name;
    private List<String> busIds;
    private List<String> driverIds;
    private List<String> monitorIds;
    private LocalDateTime departTime;
    private LocalDateTime returnedTime;
    private String remarks;
}

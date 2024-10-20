package com.wny.schoolbus.controllers;

import com.wny.schoolbus.dtos.route.RouteRequest;
import com.wny.schoolbus.dtos.route.RouteResponse;
import com.wny.schoolbus.services.impl.RouteServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v3/route-management")
public class RouteController {

    private final RouteServiceImpl routeService;

    @PostMapping
    public ResponseEntity<RouteResponse> addRoute(@RequestBody RouteRequest request) {
        RouteResponse response = routeService.addRoute(request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @GetMapping
    public ResponseEntity<List<RouteResponse>> getAllRoutes() {
        return ResponseEntity.ok(routeService.getAllRoutes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RouteResponse> getRouteById(@PathVariable String id) {
        return ResponseEntity.ok(routeService.getRouteById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRoute(@PathVariable String id) {
        routeService.deleteRoute(id);
        return ResponseEntity.ok(String.format("Route with ID: %s has been successfully deleted", id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RouteResponse> updateRoute(@PathVariable String id, @RequestBody RouteRequest request) {
        return ResponseEntity.ok().body(routeService.updateRoute(id, request));
    }
}

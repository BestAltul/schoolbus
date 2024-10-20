package com.wny.schoolbus.services;

import com.wny.schoolbus.dtos.route.RouteRequest;
import com.wny.schoolbus.dtos.route.RouteResponse;

import java.util.List;

public interface RouteService {

    RouteResponse addRoute(RouteRequest request);

    List<RouteResponse> getAllRoutes();

    RouteResponse getRouteById(String id);

    void deleteRoute(String id);

    RouteResponse updateRoute(String id, RouteRequest request);
}

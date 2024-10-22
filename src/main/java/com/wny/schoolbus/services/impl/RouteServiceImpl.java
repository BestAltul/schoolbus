package com.wny.schoolbus.services.impl;

import com.wny.schoolbus.dtos.route.RouteRequest;
import com.wny.schoolbus.dtos.route.RouteResponse;
import com.wny.schoolbus.entities.Route;
import com.wny.schoolbus.exceptions.route.ObjectNotFoundException;
import com.wny.schoolbus.repositories.BusRepository;
import com.wny.schoolbus.repositories.EmployeeRepository;
import com.wny.schoolbus.repositories.RouteRepository;
import com.wny.schoolbus.services.RouteService;
import com.wny.schoolbus.utils.route.ObjectPersistenceChecker;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RouteServiceImpl implements RouteService {

    private final RouteRepository routeRepository;
    private final EmployeeRepository employeeRepository;
    private final BusRepository busRepository;
    private final RouteMapper routeMapper;

    public RouteServiceImpl(RouteRepository routeRepository, EmployeeRepository employeeRepository,
                            BusRepository busRepository) {
        this.routeRepository = routeRepository;
        this.employeeRepository = employeeRepository;
        this.busRepository = busRepository;
        routeMapper = new RouteMapper(busRepository, employeeRepository);
    }

    @Override
    public RouteResponse addRoute(RouteRequest request) {
        request.getBusIds().forEach(id ->
                ObjectPersistenceChecker.checkObjectPersistence(id, busRepository, "Bus"));
        request.getDriverIds().forEach(id ->
                ObjectPersistenceChecker.checkObjectPersistence(id, employeeRepository, "Driver"));
        request.getMonitorIds().forEach(id ->
                ObjectPersistenceChecker.checkObjectPersistence(id, employeeRepository, "Monitor"));

        Route route = routeMapper.mapToRoute(request);
        routeRepository.save(route);

        return routeMapper.mapToRouteResponse(route);
    }

    @Override
    public List<RouteResponse> getAllRoutes() {
        return routeRepository.findAll().stream()
                .map(routeMapper::mapToRouteResponse)
                .collect(Collectors.toList());
    }

    @Override
    public RouteResponse getRouteById(String id) {
        Route requestedRoute = routeRepository.findById(id).orElseThrow(() ->
                new ObjectNotFoundException(String.format("Route with id %s not found", id)));
        return routeMapper.mapToRouteResponse(requestedRoute);
    }

    @Override
    public void deleteRoute(String id) {
        if (routeRepository.findById(id).isEmpty()) {
            throw new ObjectNotFoundException(String.format("Route with id %s not found", id));
        }
        routeRepository.deleteById(id);
    }

    @Override
    public RouteResponse updateRoute(String id, RouteRequest request) {
        Route route = routeRepository.findById(id).orElseThrow(() ->
                new ObjectNotFoundException(String.format("Route with id %s not found", id)));
        routeMapper.mapToRoute(request, route);
        routeRepository.save(route);

        return routeMapper.mapToRouteResponse(route);
    }
}

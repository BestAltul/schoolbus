package com.wny.schoolbus.services.impl;

import com.wny.schoolbus.dtos.route.RouteRequest;
import com.wny.schoolbus.dtos.route.RouteResponse;
import com.wny.schoolbus.entities.Employee;
import com.wny.schoolbus.entities.Route;
import com.wny.schoolbus.entities.impl.SchoolBusImpl;
import com.wny.schoolbus.exceptions.route.ObjectNotFoundException;
import com.wny.schoolbus.repositories.BusRepository;
import com.wny.schoolbus.repositories.EmployeeRepository;
import com.wny.schoolbus.repositories.RouteRepository;
import com.wny.schoolbus.services.RouteService;
import com.wny.schoolbus.utils.route.ObjectPersistenceChecker;
import com.wny.schoolbus.utils.route.EntityListConverter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RouteServiceImpl implements RouteService {

    private final RouteRepository routeRepository;
    private final EmployeeRepository employeeRepository;
    private final BusRepository busRepository;
    private ModelMapper modelMapper;

    @Override
    public RouteResponse addRoute(RouteRequest request) {
        request.getBusIds().forEach(id ->
                ObjectPersistenceChecker.checkObjectPersistence(id, busRepository, "Bus"));
        request.getDriverIds().forEach(id ->
                ObjectPersistenceChecker.checkObjectPersistence(id, employeeRepository, "Driver"));
        request.getMonitorIds().forEach(id ->
                ObjectPersistenceChecker.checkObjectPersistence(id, employeeRepository, "Monitor"));

        Route route = routeRepository.save(Route.builder()
                .name(request.getName())
                .departTime(request.getDepartTime())
                .returnedTime(request.getReturnedTime())
                .remarks(request.getRemarks())
                .buses(mapToBuses(request.getBusIds()))
                .drivers(mapToEmployee(request.getDriverIds()))
                .monitors(mapToEmployee(request.getMonitorIds()))
                .build()
        );

        return mapToRouteResponse(route);
    }

    private RouteResponse mapToRouteResponse(Route route) {
        return RouteResponse.builder()
                .id(route.getId())
                .name(route.getName())
                .busIds(route.getBuses().stream()
                        .map(SchoolBusImpl::getId)
                        .collect(Collectors.toList())
                )
                .driverIds(route.getDrivers().stream()
                        .map(Employee::getId)
                        .collect(Collectors.toList())
                )
                .monitorIds(route.getMonitors().stream()
                        .map(Employee::getId)
                        .collect(Collectors.toList())
                )
                .departTime(route.getDepartTime())
                .returnedTime(route.getReturnedTime())
                .remarks(route.getRemarks())
                .build();
    }

    private List<SchoolBusImpl> mapToBuses(List<String> busIds) {
        return busIds.stream()
                .map(busRepository::findById)
                .flatMap(Optional::stream)
                .collect(Collectors.toList());
    }

    private List<Employee> mapToEmployee(List<String> employeeIds) {
        return employeeIds.stream()
                .map(employeeRepository::findById)
                .flatMap(Optional::stream)
                .collect(Collectors.toList());
    }

    @Override
    public List<RouteResponse> getAllRoutes() {
        return routeRepository.findAll().stream()
                .map(this::mapToRouteResponse)
                .collect(Collectors.toList());
    }

    @Override
    public RouteResponse getRouteById(String id) {
        Route requestedRoute = routeRepository.findById(id).orElseThrow(() ->
                new ObjectNotFoundException(String.format("Route with id %s not found", id)));
        return mapToRouteResponse(requestedRoute);
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

        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        TypeMap<RouteRequest, Route> typeMap = modelMapper.createTypeMap(RouteRequest.class, Route.class);
        typeMap.addMappings(mapper -> mapper.using(new EntityListConverter(busRepository))
                .map(RouteRequest::getBusIds, Route::setBuses));
        typeMap.addMappings(mapper -> mapper.using(new EntityListConverter(employeeRepository))
                .map(RouteRequest::getDriverIds, Route::setDrivers));
        typeMap.addMappings(mapper -> mapper.using(new EntityListConverter(employeeRepository))
                .map(RouteRequest::getMonitorIds, Route::setMonitors));

        modelMapper.map(request, route);
        routeRepository.save(route);

        return mapToRouteResponse(route);
    }
}

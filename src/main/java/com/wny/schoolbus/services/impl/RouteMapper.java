package com.wny.schoolbus.services.impl;

import com.wny.schoolbus.dtos.route.RouteRequest;
import com.wny.schoolbus.dtos.route.RouteResponse;
import com.wny.schoolbus.entities.Route;
import com.wny.schoolbus.repositories.BusRepository;
import com.wny.schoolbus.repositories.EmployeeRepository;
import com.wny.schoolbus.utils.route.BusListToIdListConverter;
import com.wny.schoolbus.utils.route.EmployeeListToIdListConverter;
import com.wny.schoolbus.utils.route.EntityListConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

@Component
public class RouteMapper {

    private final ModelMapper modelMapper;
    private final BusRepository busRepository;
    private final EmployeeRepository employeeRepository;

    public RouteMapper(BusRepository busRepository, EmployeeRepository employeeRepository) {
        this.busRepository = busRepository;
        this.employeeRepository = employeeRepository;
        modelMapper = configure();
    }

    public Route mapToRoute(RouteRequest request) {
       return modelMapper.map(request, Route.class);
    }

    public void mapToRoute(RouteRequest request, Route route) {
        modelMapper.map(request, route);
    }

    public RouteResponse mapToRouteResponse(Route route) {
        return modelMapper.map(route, RouteResponse.class);
    }

    public ModelMapper configure() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);

        TypeMap<RouteRequest, Route> routeRequestToRouteMap = modelMapper.createTypeMap(RouteRequest.class, Route.class);
        routeRequestToRouteMap.addMappings(mapper -> mapper.using(new EntityListConverter(busRepository))
                .map(RouteRequest::getBusIds, Route::setBuses));
        routeRequestToRouteMap.addMappings(mapper -> mapper.using(new EntityListConverter(employeeRepository))
                .map(RouteRequest::getDriverIds, Route::setDrivers));
        routeRequestToRouteMap.addMappings(mapper -> mapper.using(new EntityListConverter(employeeRepository))
                .map(RouteRequest::getMonitorIds, Route::setMonitors));

        TypeMap<Route, RouteResponse> routeToRouteResponseMap =
                modelMapper.createTypeMap(Route.class, RouteResponse.class);
        routeToRouteResponseMap.addMappings(mapper -> mapper.using(new BusListToIdListConverter())
                .map(Route::getBuses, RouteResponse::setBusIds));
        routeToRouteResponseMap.addMappings(mapper -> mapper.using(new EmployeeListToIdListConverter())
                .map(Route::getDrivers, RouteResponse::setDriverIds));
        routeToRouteResponseMap.addMappings(mapper -> mapper.using(new EmployeeListToIdListConverter())
                .map(Route::getMonitors, RouteResponse::setMonitorIds));

        return modelMapper;
    }
}

package com.wny.schoolbus.utils.route;

import com.wny.schoolbus.entities.impl.SchoolBusImpl;
import org.modelmapper.AbstractConverter;

import java.util.List;
import java.util.stream.Collectors;

public class BusListToIdListConverter extends AbstractConverter<List<SchoolBusImpl>, List<String>> {

    @Override
    protected List<String> convert(List<SchoolBusImpl> source) {
        return source.stream()
                .map(SchoolBusImpl::getId)
                .collect(Collectors.toList());
    }
}

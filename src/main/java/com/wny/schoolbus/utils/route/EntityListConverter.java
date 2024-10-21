package com.wny.schoolbus.utils.route;

import lombok.RequiredArgsConstructor;
import org.modelmapper.AbstractConverter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class EntityListConverter extends AbstractConverter<List<String>, List<Object>> {

    private final JpaRepository<?, String> repository;

    @Override
    protected List<Object> convert(List<String> source) {
        return source.stream()
                .map(repository::findById)
                .flatMap(Optional::stream)
                .collect(Collectors.toList());
    }
}

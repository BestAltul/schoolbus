package com.wny.schoolbus.utils;

import com.wny.schoolbus.entities.impl.SchoolBusImpl;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
public class SchoolBusRevision {
    private SchoolBusImpl schoolBus;
    private Date revisionDate;
}

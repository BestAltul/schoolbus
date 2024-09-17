package com.wny.schoolbus.entities.impl;

import com.wny.schoolbus.entities.Terminal;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class TerminalImpl implements Terminal {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;

}

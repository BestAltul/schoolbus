package com.wny.schoolbus.entities.impl;

import com.wny.schoolbus.entities.Terminal;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="terminal")
@Getter
@Setter
@AllArgsConstructor
public class TerminalImpl implements Terminal {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name="name")
    private String name;

}

package com.wny.schoolbus.entities.impl;

import com.wny.schoolbus.entities.SimCardHistory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="sim_card_history")
public class SimCardHistoryImpl implements SimCardHistory{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "dash_cam_id")
    private DashCamImpl dashCam;

    @ManyToOne
    @JoinColumn(name = "sim_card_id")
    private SimCardImpl simCard;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;
}
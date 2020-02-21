package com.gabia.gyebalja.service;

import com.gabia.gyebalja.dto.statistics.StatisticsMainYearResponseDto;
import com.gabia.gyebalja.repository.EducationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.ArrayList;

@RequiredArgsConstructor
@Transactional
@Service
public class StatisticsService {

    @PersistenceContext
    EntityManager em;

    private final EducationRepository educationRepository;

    public StatisticsMainYearResponseDto getMainStatisticsWtihYear(){

        ArrayList years = new ArrayList();
        ArrayList months = new ArrayList();

        int yearLoop = 5;
        int monthLoop = 12;

        LocalDate currentDate = LocalDate.now(); // 2020-01-01
        int currentYear = currentDate.getYear(); // 2020
        int currentMonth = currentDate.getMonthValue();

        for(int i = 0; i < yearLoop; i++){
            years.add(currentYear - i);
        }
        for(int i = 0; i < monthLoop; i++){
            months.add(currentMonth - i);
        }

        educationRepository.findStatisticsMainWithYear();
    }
}

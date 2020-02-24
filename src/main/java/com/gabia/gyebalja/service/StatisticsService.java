package com.gabia.gyebalja.service;

import com.gabia.gyebalja.dto.statistics.StatisticsMainCategoryResponseDto;
import com.gabia.gyebalja.dto.statistics.StatisticsMainMonthResponseDto;
import com.gabia.gyebalja.dto.statistics.StatisticsMainTagResponseDto;
import com.gabia.gyebalja.dto.statistics.StatisticsMainYearResponseDto;
import com.gabia.gyebalja.repository.EduTagRepository;
import com.gabia.gyebalja.repository.EducationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class StatisticsService {

    @PersistenceContext
    EntityManager em;

    private final EducationRepository educationRepository;
    private final EduTagRepository eduTagRepository;

    public StatisticsMainYearResponseDto getMainStatisticsWithYear(){
        int yearPage = 0;
        int yearSize = 5;
        List<ArrayList<Long>> response = educationRepository.getMainStatisticsWithYear2(PageRequest.of(yearPage, yearSize));
        ArrayList<Long> years = new ArrayList<>();
        ArrayList<Long> totalEducationHourOfEmployees = new ArrayList<>();
        ArrayList<Long> totalEducationNumberOfEmployees = new ArrayList<>();

        int yearIdx = 0, hourIdx = 1, numberIdx = 2;
        for (ArrayList<Long> row : response) {
            years.add(row.get(yearIdx));
            totalEducationHourOfEmployees.add(row.get(hourIdx));
            totalEducationNumberOfEmployees.add(row.get(numberIdx));
        }

        StatisticsMainYearResponseDto statisticsMainYearResponseDto = new StatisticsMainYearResponseDto(years, totalEducationHourOfEmployees, totalEducationNumberOfEmployees);

        return statisticsMainYearResponseDto;
    }

    public StatisticsMainMonthResponseDto getMainStatisticsWithMonth(){
        int monthPage = 0;
        int monthSize = 12;
        List<ArrayList<String>> response = educationRepository.getMainStatisticsWithMonth(PageRequest.of(monthPage, monthSize));
        ArrayList<String> months = new ArrayList<>();
        ArrayList<Long> totalEducationHourOfEmployees = new ArrayList<>();
        ArrayList<Long> totalEducationNumberOfEmployees = new ArrayList<>();

        int monthIdx = 0, hourIdx = 1, numberIdx = 2;
        for (ArrayList<String> row : response) {
            months.add(row.get(monthIdx));
            totalEducationHourOfEmployees.add(Long.parseLong(row.get(hourIdx)));
            totalEducationNumberOfEmployees.add(Long.parseLong(row.get(numberIdx)));
        }

        StatisticsMainMonthResponseDto statisticsMainMonthResponseDto = new StatisticsMainMonthResponseDto(months, totalEducationHourOfEmployees, totalEducationNumberOfEmployees);

        return statisticsMainMonthResponseDto;
    }

    public StatisticsMainCategoryResponseDto getMainStatisticsWithCategory(){
        int categoryPage = 0;
        int categorySize = 3;
        List<ArrayList<String>> response = educationRepository.getMainStatisticsWithCategory(PageRequest.of(categoryPage, categorySize));
        ArrayList<String> names = new ArrayList<>();
        ArrayList<Long> totalNumber = new ArrayList<>();

        int nameIdx = 0, numberIdx = 1;
        for (ArrayList<String> row : response) {
            names.add(row.get(nameIdx));
            totalNumber.add(Long.parseLong(row.get(numberIdx)));
        }

        StatisticsMainCategoryResponseDto statisticsMainCategoryResponseDto = new StatisticsMainCategoryResponseDto(names, totalNumber);

        return statisticsMainCategoryResponseDto;
    }

    public StatisticsMainTagResponseDto getMainStatisticsWithTag(){
        int tagPage = 0;
        int tagSize = 3;
        List<ArrayList<String>> response = eduTagRepository.getMainStatisticsWithTag(PageRequest.of(tagPage, tagSize));
        ArrayList<String> names = new ArrayList<>();
        ArrayList<Long> totalCount = new ArrayList<>();

        int nameIdx = 0, numberIdx = 1;
        for (ArrayList<String> row : response) {
            names.add(row.get(nameIdx));
            totalCount.add(Long.parseLong(row.get(numberIdx)));
        }

        StatisticsMainTagResponseDto statisticsMainTagResponseDto = new StatisticsMainTagResponseDto(names, totalCount);

        return statisticsMainTagResponseDto;
    }
}

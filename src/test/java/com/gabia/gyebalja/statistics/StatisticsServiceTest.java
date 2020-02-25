package com.gabia.gyebalja.statistics;

import com.gabia.gyebalja.dto.statistics.StatisticsMainMonthResponseDto;
import com.gabia.gyebalja.dto.statistics.StatisticsMainCategoryResponseDto;
import com.gabia.gyebalja.dto.statistics.StatisticsMainTagResponseDto;
import com.gabia.gyebalja.dto.statistics.StatisticsMainYearResponseDto;
import com.gabia.gyebalja.repository.StatisticsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@SpringBootTest
public class StatisticsServiceTest {

    @Autowired private StatisticsRepository statisticsRepository;

    @Test
    public void getMainStatisticsWithYear(){
        int yearPage = 0;
        int yearSize = 5;
        List<ArrayList<String>> response = statisticsRepository.getMainStatisticsWithYear(PageRequest.of(yearPage, yearSize));
        ArrayList<String> years = new ArrayList<>();
        ArrayList<Long> totalEducationHourOfEmployees = new ArrayList<>();
        ArrayList<Long> totalEducationNumberOfEmployees = new ArrayList<>();

        int yearIdx = 0, hourIdx = 1, numberIdx = 2;
        for (ArrayList<String> row : response) {
            years.add(row.get(yearIdx));
            totalEducationHourOfEmployees.add(Long.parseLong(row.get(hourIdx)));
            totalEducationNumberOfEmployees.add(Long.parseLong(row.get(numberIdx)));
        }

        StatisticsMainYearResponseDto statisticsMainYearResponseDto = new StatisticsMainYearResponseDto(years, totalEducationHourOfEmployees, totalEducationNumberOfEmployees);
        System.out.println(statisticsMainYearResponseDto.toString());
    }

    @Test
    public void getMainStatisticsWithMonth(){
        int monthPage = 0;
        int monthSize = 12;
        List<ArrayList<String>> response = statisticsRepository.getMainStatisticsWithMonth(PageRequest.of(monthPage, monthSize));
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
        System.out.println(statisticsMainMonthResponseDto.toString());
    }

    @Test
    public void getMainStatisticsWithCategory(){
        int categoryPage = 0;
        int categorySize = 3;
        List<ArrayList<String>> response = statisticsRepository.getMainStatisticsWithCategory(PageRequest.of(categoryPage, categorySize));
        ArrayList<String> names = new ArrayList<>();
        ArrayList<Long> totalNumber = new ArrayList<>();

        int nameIdx = 0, numberIdx = 1;
        for (ArrayList<String> row : response) {
            names.add(row.get(nameIdx));
            totalNumber.add(Long.parseLong(row.get(numberIdx)));
        }

        StatisticsMainCategoryResponseDto statisticsMainCategoryResponseDto = new StatisticsMainCategoryResponseDto(names, totalNumber);
        System.out.println(statisticsMainCategoryResponseDto.toString());
    }

    @Test
    public void getMainStatisticsWithTag(){
        int tagPage = 0;
        int tagSize = 3;
        List<ArrayList<String>> response = statisticsRepository.getMainStatisticsWithTag(PageRequest.of(tagPage, tagSize));
        ArrayList<String> names = new ArrayList<>();
        ArrayList<Long> totalCount = new ArrayList<>();

        int nameIdx = 0, numberIdx = 1;
        for (ArrayList<String> row : response) {
            names.add(row.get(nameIdx));
            totalCount.add(Long.parseLong(row.get(numberIdx)));
        }

        StatisticsMainTagResponseDto statisticsMainTagResponseDto = new StatisticsMainTagResponseDto(names, totalCount);
        System.out.println(statisticsMainTagResponseDto.toString());
    }
}

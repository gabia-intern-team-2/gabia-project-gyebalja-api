package com.gabia.gyebalja.service;

import com.gabia.gyebalja.domain.User;
import com.gabia.gyebalja.dto.statistics.*;
import com.gabia.gyebalja.repository.EduTagRepository;
import com.gabia.gyebalja.repository.EducationRepository;
import com.gabia.gyebalja.repository.StatisticsRepository;
import com.gabia.gyebalja.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class StatisticsService {

    private final StatisticsRepository statisticsRepository;
    private final UserRepository userRepository;
    /**
     *
     * 메인 페이지 통계 Service
     */
    public StatisticsMainYearResponseDto getMainStatisticsWithYear(){
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

        return statisticsMainYearResponseDto;
    }

    public StatisticsMainMonthResponseDto getMainStatisticsWithMonth(){
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

        return statisticsMainMonthResponseDto;
    }

    public StatisticsMainCategoryResponseDto getMainStatisticsWithCategory(){
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

        return statisticsMainCategoryResponseDto;
    }

    public StatisticsMainTagResponseDto getMainStatisticsWithTag(){
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

        return statisticsMainTagResponseDto;
    }

    /**
     *
     * 개인 교육 관리 페이지 통계 Service
     */
    // 통계 - 당해년도의 월별 교육 건수, 시간
    public StatisticsEducationMonthResponseDto getEducationStatisticsWithMonth(Long userId) {
        String currentYear = Integer.toString(LocalDate.now().getYear());
        List<ArrayList<String>> response = statisticsRepository.getEducationStatisticsWithMonth(userId, currentYear);
        ArrayList<String> months = new ArrayList<>();
        ArrayList<Long> EducationHoursOfUser = new ArrayList<>();
        ArrayList<Long> EducationNumbersOfUser = new ArrayList<>();

        int monthIdx = 0, hourIdx = 1, numberIdx = 2;
        exit: for(int i = 1; i <= 12; i++) {
            String monthFormat;
            if( i < 10 ) {
                monthFormat = "0" + Integer.toString(i);
            } else {
                monthFormat = Integer.toString(i);
            }
            months.add(monthFormat);
            for (ArrayList<String> row : response) {
                if(monthFormat.equals(row.get(monthIdx))) {
                    EducationHoursOfUser.add(Long.parseLong(row.get(hourIdx)));
                    EducationNumbersOfUser.add(Long.parseLong(row.get(numberIdx)));
                    continue exit;
                }
            }
            EducationHoursOfUser.add(0L);
            EducationNumbersOfUser.add(0L);

        }

        return new StatisticsEducationMonthResponseDto(currentYear, months, EducationHoursOfUser, EducationNumbersOfUser);
    }

    // 통계 - 누적 개인 최다 카테고리
    public StatisticsEducationCategoryResponseDto getEducationStatisticsWithCategory(Long userId) {
        int categoryPage = 0;
        int categorySize = 1;
        List<ArrayList<String>> response = statisticsRepository.getEducationStatisticsWithCategory(userId, PageRequest.of(categoryPage, categorySize));
        String categoryName = "없음";
        Long totalNumber = 0L;

        int nameIdx = 0, numberIdx = 1;
        for (ArrayList<String> row : response) {
            categoryName = row.get(nameIdx);
            totalNumber = Long.parseLong(row.get(numberIdx));
        }

        return new StatisticsEducationCategoryResponseDto(categoryName, totalNumber);
    }

    // 통계 - 누적 개인 TOP 3 태그
    public StatisticsEducationTagResponseDto getEducationStatisticsWithTag(Long userId) {
        int tagPage = 0;
        int tagSize = 3;
        List<ArrayList<String>> response = statisticsRepository.getEducationStatisticsWithTag(userId, PageRequest.of(tagPage, tagSize));
        ArrayList<String> tagNames = new ArrayList<>();
        ArrayList<Long> totalCount = new ArrayList<>();

        int nameIdx = 0, numberIdx = 1;
        for (ArrayList<String> row : response) {
            tagNames.add(row.get(nameIdx));
            totalCount.add(Long.parseLong(row.get(numberIdx)));
        }

        return new StatisticsEducationTagResponseDto(tagNames, totalCount);
    }

    // 통계 - 당해년도 사용자 vs 회사
    public StatisticsEducationHourResponseDto getEducationStatisticsWithHour(Long userId) {
        String currentYear = Integer.toString(LocalDate.now().getYear());
        // 당해년도 개인 총 교육시간
        Long individualTotalHours = statisticsRepository.getEducationStatisticsWithIndividualTotalHours(userId, currentYear);

        // 사용자 수
        long totalUsers = userRepository.count();

        // 당해년도 회사 총 교육시간
        Long companyTotalHours = statisticsRepository.getEducationStatisticsWithCompanyTotalHours(currentYear);

        return new StatisticsEducationHourResponseDto(individualTotalHours, companyTotalHours/totalUsers);
    }

    // 통계 - 당해년도 부서 내 등수
    public StatisticsEducationRankResponseDto getEducationStatisticsWithRank(Long userId) {
        String currentYear = Integer.toString(LocalDate.now().getYear());
        // 사용자가 속한 부서 ID 조회
        Optional<User> findUser = userRepository.findById(userId);
        Long deptId = findUser.get().getDepartment().getId();

        List<ArrayList<String>> response = statisticsRepository.getEducationStatisticsWithRank(deptId, currentYear);
        // 아직 동 등수 미처리
        int rank = 0;
        for(int i = 0; i < response.size(); i++) {

            if( response.get(i).get(0).equals(Long.toString(userId))) {
                rank = i+1;
                break;
            }
        }

        return new StatisticsEducationRankResponseDto(rank, response.size());
    }
}
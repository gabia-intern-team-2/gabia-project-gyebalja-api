package com.gabia.gyebalja.service;

import com.gabia.gyebalja.common.exception.NotExistDataException;
import com.gabia.gyebalja.domain.Department;
import com.gabia.gyebalja.domain.User;
import com.gabia.gyebalja.dto.rank.RankResponseDto;
import com.gabia.gyebalja.repository.DepartmentRepository;
import com.gabia.gyebalja.repository.RankRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Author : 정태균
 * Part : All
 */

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RankService {

    private final RankRepository rankRepository;
    private final DepartmentRepository departmentRepository;

    /**
     * 부서별 랭크 페이지 Service
     */
    public List<RankResponseDto> getRankByDeptId(Long deptId) {

        Department findDept = departmentRepository.findById(deptId).orElseThrow(() -> new NotExistDataException("존재하지 않는 부서입니다."));
        String currentYear = Integer.toString(LocalDate.now().getYear());
        List<RankResponseDto> rankListDto = new ArrayList<RankResponseDto>();
        int rank = 1;

        List<ArrayList<Object>> rankByDeptId = rankRepository.getRankByDeptId(deptId, currentYear);
        int totalHoursIdx = 0, totalCountsIdx = 1, userIdx = 2;

        for (ArrayList<Object> objects : rankByDeptId) {
            User user = (User) objects.get(userIdx);

            int totalHours = (objects.get(totalHoursIdx) != null) ? Integer.parseInt(objects.get(totalHoursIdx).toString()) : 0;

            rankListDto.add(RankResponseDto.builder().rank(rank).totalHour(totalHours).totalCount(Integer.parseInt(objects.get(totalCountsIdx).toString())).user(user).build());
            rank++;
        }
        return rankListDto;
    }
}

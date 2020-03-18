package com.gabia.gyebalja.service;

import com.gabia.gyebalja.common.exception.NotExistDataException;
import com.gabia.gyebalja.domain.Department;
import com.gabia.gyebalja.domain.User;
import com.gabia.gyebalja.dto.rank.RankResponseDto;
import com.gabia.gyebalja.repository.DepartmentRepository;
import com.gabia.gyebalja.repository.RankRepository;
import com.gabia.gyebalja.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Author : 정태균
 * Part : All
 */

@RequiredArgsConstructor //final의 필드만 가지고 생성자를 만들어줌
@Transactional(readOnly = true)
@Service
public class RankService {

    private final RankRepository rankRepository;
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;

    /**
     * 부서별 랭크 페이지 Service
     */
    public List<RankResponseDto> getRankByDeptId(Long deptId) {

        List<RankResponseDto> rankListDto = new ArrayList<RankResponseDto>();
        int rank = 1;
        Department findDept = departmentRepository.findById(deptId).orElseThrow(() -> new NotExistDataException("존재하지 않는 부서입니다."));

        List<ArrayList<String>> rankByDeptId = rankRepository.getRankByDeptId(deptId);
        for (ArrayList<String> results : rankByDeptId) {
            Optional<User> user = userRepository.findById(Long.parseLong(results.get(2)));
            int totalHours = 0;
            if (results.get(0) != null)
                totalHours = Integer.parseInt(results.get(0));

            RankResponseDto rankResponseDto = RankResponseDto.builder().rank(rank).totalHour(totalHours).totalCount(Integer.parseInt(results.get(1))).user(user.get()).build();

            rankListDto.add(rankResponseDto);
            rank++;
        }
        return rankListDto;
    }
}

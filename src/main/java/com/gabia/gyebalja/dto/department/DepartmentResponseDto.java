package com.gabia.gyebalja.dto.department;

import com.gabia.gyebalja.domain.Department;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Getter
public class DepartmentResponseDto {

    private Long id;
    private String name;
    private int depth;
    private Long parentDepartmentId;
    private String parentDepartmentName;

    public DepartmentResponseDto(Department department){
        this.id = department.getId();
        this.name = department.getName();
        this.depth = department.getDepth();
        if(department.getParentDepartment() != null) {
            this.parentDepartmentId = department.getParentDepartment().getId();
            this.parentDepartmentName = department.getParentDepartment().getName();
        } else{
            this.parentDepartmentId = 0L;
            this.parentDepartmentName = "NULL";
        }
    }

}

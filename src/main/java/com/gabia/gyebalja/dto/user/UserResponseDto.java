package com.gabia.gyebalja.dto.user;

import com.gabia.gyebalja.domain.GenderType;
import com.gabia.gyebalja.domain.Likes;
import com.gabia.gyebalja.domain.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserResponseDto {
    private Long id;
    private Long gabiaUserNo;
    private String email;
    private String name;
    private String engName;
    private GenderType gender;
    private String phone;
    private String tel;
    private Long positionId;
    private String positionName;
    private Long deptId;
    private String profileImg;

    public UserResponseDto(User user){
        this.id = user.getId();
        this.gabiaUserNo = user.getGabiaUserNo();
        this.email = user.getEmail();
        this.name = user.getName();
        this.engName = user.getEngName();
        this.gender = user.getGender();
        this.phone = user.getPhone();
        this.tel = user.getTel();
        this.positionId = user.getPositionId();
        this.positionName = user.getPositionName();
        this.deptId = user.getDepartment().getId();
        this.profileImg = user.getProfileImg();
    }
}
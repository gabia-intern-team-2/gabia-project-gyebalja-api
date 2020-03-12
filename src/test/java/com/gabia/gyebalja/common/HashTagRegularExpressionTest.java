package com.gabia.gyebalja.common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Author : 정태균
 * Part : All
 */

@Transactional
@DataJpaTest
public class HashTagRegularExpressionTest {
    
    @Test
    @DisplayName("해시태그 소문자 변환 테스트")
    public void toLowerCaseHashTag() throws Exception {
        //given
        String hashTagString = "#SPRING";
        String toLower = hashTagString.toLowerCase();
        HashTagRegularExpression hashTagRegularExpression = new HashTagRegularExpression();
        //when
        ArrayList<String> extractHashTag = hashTagRegularExpression.getExtractHashTag(hashTagString);
        //then
        assertThat(extractHashTag.get(0)).isEqualTo("#spring");
    }

    @Test
    @DisplayName("해시태그 추출 테스트")
    public void getExtractHashTag() throws Exception {
        //given
        String hashTagString = "#spring #Vue # #안녕 하세요 #HTML $Test";
        HashTagRegularExpression hashTagRegularExpression = new HashTagRegularExpression();
        //when
        ArrayList<String> extractHashTag = hashTagRegularExpression.getExtractHashTag(hashTagString);
        //then
        assertThat(extractHashTag.size()).isEqualTo(4);  //#spring, #Vue, #안녕, #HTML 만 추출됨.
        assertThat(extractHashTag.get(0)).isEqualTo("#spring");
    }

    @Test
    @DisplayName("해시태그 중복 문자열 제거 테스트")
    public void removeDuplication() throws Exception {
        //given
        ArrayList<String> extractedHashTag = new ArrayList<String>();
        extractedHashTag.add("#spring");
        extractedHashTag.add("#spring");
        extractedHashTag.add("#spring");
        extractedHashTag.add("#html");
        extractedHashTag.add("#html");
        extractedHashTag.add("#vue");
        //when
        HashTagRegularExpression hashTagRegularExpression = new HashTagRegularExpression();
        ArrayList<String> removeDuplicationHashTag = hashTagRegularExpression.removeDuplication(extractedHashTag);
        //then
        assertThat(removeDuplicationHashTag.size()).isEqualTo(3);
    }
}

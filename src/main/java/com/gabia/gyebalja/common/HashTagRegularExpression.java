package com.gabia.gyebalja.common;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author : 정태균
 * Part : All
 */

public class HashTagRegularExpression {

    public ArrayList<String> getExtractHashTag(String hashtag) {

        ArrayList<String> resultHashtag = new ArrayList<>();

        Pattern p = Pattern.compile("\\#([0-9a-zA-Z가-힣]*)");
        Matcher m = p.matcher(hashtag);
        String extractHashTag = null;

        while(m.find()) {
            extractHashTag = sepcialCharacter_replace(m.group());

            if(extractHashTag != null) {
                resultHashtag.add(extractHashTag);
            }
        }

        return resultHashtag;
    }

    public String sepcialCharacter_replace(String str) {
        str = StringUtils.replace(str, "-_+=!@#$%^&*()[]{}|\\;:'\"<>,.?/~`） ","").toLowerCase();
        if(str.length() < 2) {
            return null;
        }

        return str;
    }

    // 문자열 중복 제거 메서드
    public ArrayList<String> removeDuplication(ArrayList<String> extractedHashTag) {
        ArrayList<String> result = new ArrayList<String>();

        for (int i = 0; i < extractedHashTag.size(); i++) {
            if (!result.contains(extractedHashTag.get(i))) {
                result.add(extractedHashTag.get(i));
            }
        }
        return result;
    }
}

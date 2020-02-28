package com.gabia.gyebalja.common;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        System.out.println("str = " + str);
        if(str.length() < 2) {
            return null;
        }

        return str;
    }

}

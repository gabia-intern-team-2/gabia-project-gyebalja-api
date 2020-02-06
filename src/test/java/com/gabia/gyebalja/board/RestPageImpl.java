package com.gabia.gyebalja.board;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

/**
 *  Test 코드에서 Page<T>타입을 받아주기 위해 필요 (/api/v1/boards등의 반환타입)
 *  예제1 - Page responseEntity = restTemplate.getForObject(url, RestPageImpl.class);
 *  방법1 - https://stackoverflow.com/questions/34099559/how-to-consume-pageentity-response-using-spring-resttemplate/44895867
 *  방법2 - https://stackoverflow.com/questions/34647303/spring-resttemplate-with-paginated-api
 */
public class RestPageImpl<T> extends PageImpl<T> {
    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public RestPageImpl(@JsonProperty("content") List<T> content,
                        @JsonProperty("number") int number,
                        @JsonProperty("size") int size,
                        @JsonProperty("totalElements") Long totalElements,
                        @JsonProperty("pageable") JsonNode pageable,
                        @JsonProperty("last") boolean last,
                        @JsonProperty("totalPages") int totalPages,
                        @JsonProperty("sort") JsonNode sort,
                        @JsonProperty("first") boolean first,
                        @JsonProperty("numberOfElements") int numberOfElements) {

        super(content, PageRequest.of(number, size), totalElements);
    }

    public RestPageImpl(List<T> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public RestPageImpl(List<T> content) {
        super(content);
    }

    public RestPageImpl() {
        super(new ArrayList());
    }
}


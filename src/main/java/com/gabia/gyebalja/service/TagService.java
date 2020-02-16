package com.gabia.gyebalja.service;

import com.gabia.gyebalja.domain.Tag;
import com.gabia.gyebalja.dto.tag.TagRequestDto;
import com.gabia.gyebalja.dto.tag.TagResponseDto;
import com.gabia.gyebalja.exception.NotExistTagException;
import com.gabia.gyebalja.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class TagService {

    private final TagRepository tagRepository;

    /** 등록 - tag 한 건 (태그 등록) */
    @Transactional
    public Long postOneTag(TagRequestDto tagRequestDto) {
        Long tagId = tagRepository.save(Tag.builder()
                                        .name(tagRequestDto.getName())
                                        .build()).getId();

        return tagId;
    }

    /** 조회 - tag 한 건 */
    public TagResponseDto getOneTag(Long id) {
        Optional<Tag> findTag = tagRepository.findById(id);

        if(!findTag.isPresent())
            throw new NotExistTagException("존재하지 않는 태그입니다.");

        return TagResponseDto.builder()
                .id(findTag.get().getId())
                .name(findTag.get().getName())
                .build();
    }

    /** 수정 - tag 한 건 */
    @Transactional
    public Long putOneTag(Long id, TagRequestDto tagRequestDto) {
        Optional<Tag> tag = tagRepository.findById(id); //1차 캐시에 저장

        if(!tag.isPresent())
            throw new NotExistTagException("존재하지 않는 태그입니다.");

        tag.get().changeTagName(tagRequestDto.getName());

        return id;
    }

    /** 삭제 - tag 한 건 */
    @Transactional
    public Long deleteOneTag(Long id) {
        tagRepository.deleteById(id);

        return id;
    }

    /** 조회 - tag 전체 (전체조회) */ //해당 태그를 모두 뿌려줄거기때문에 페이징 불필요.
    public List<TagResponseDto> getAllTag() {
        List<Tag> tags = tagRepository.findAll();
        List<TagResponseDto> tagResponseDtos = tags.stream()
                                                    .map(t -> TagResponseDto.builder().id(t.getId()).name(t.getName()).build())
                                                    .collect(Collectors.toList());// Entity를 노출 시키지않고 Dto로 변환후 리턴

        return tagResponseDtos;
    }
}

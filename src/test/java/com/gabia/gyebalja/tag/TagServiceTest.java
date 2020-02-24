package com.gabia.gyebalja.tag;

import com.gabia.gyebalja.domain.Tag;
import com.gabia.gyebalja.dto.tag.TagRequestDto;
import com.gabia.gyebalja.dto.tag.TagResponseDto;
import com.gabia.gyebalja.repository.TagRepository;
import com.gabia.gyebalja.service.TagService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
public class TagServiceTest {

    @PersistenceContext
    EntityManager em;
    @Autowired
    TagService tagService;
    @Autowired
    TagRepository tagRepository;

    /**
     * 등록 - tag 한 건 (단건 등록)
     */
    @Test
    @DisplayName("TagService.postOneTag() 테스트 (단건 저장)")
    public void postOneTag() throws Exception {
        //given
        TagRequestDto tagRequestDto = TagRequestDto.builder()
                .name("Spring")
                .build();
        //when
        Long tagId = tagService.postOneTag(tagRequestDto);
        em.clear();
        Tag findTag = tagRepository.findById(tagId).get();
        //then
        assertThat(tagId).isEqualTo(findTag.getId());
        assertThat(tagRequestDto.getName()).isEqualTo(findTag.getName());
    }

    /**
     * 조회 - tag 한 건 (단건 조회)
     */
    @Test
    @DisplayName("TagService.getOneTag() 테스트 (단건 조회)")
    public void getOneTag() throws Exception {
        //given
       TagRequestDto tagRequestDto = TagRequestDto.builder()
                .name("Spring")
                .build();
        Long tagId = tagService.postOneTag(tagRequestDto);
        em.clear();
        //when
        TagResponseDto findTag = tagService.getOneTag(tagId);
        //then
        assertThat(findTag.getId()).isEqualTo(tagId);
        assertThat(findTag.getName()).isEqualTo(tagRequestDto.getName());
    }

    /**
     * 수정 - tag 한 건 (단건 수정)
     */
    @Test
    @DisplayName("TagService.putOneTag() 테스트 (단건 수정)")
    public void putOneTag() throws Exception {
        //given
        String updateName = "HTML";
        TagRequestDto tagRequestDto = TagRequestDto.builder()
                .name("Spring")
                .build();
        Long tagId = tagService.postOneTag(tagRequestDto);
        //when
        tagRequestDto.setName(updateName);
        Long updateId = tagService.putOneTag(tagId, tagRequestDto);
        em.flush(); //업데이트 쿼리를 보기위함.
        Tag findUpdateTag = tagRepository.findById(updateId).get();
        //then
        assertThat(findUpdateTag.getId()).isEqualTo(tagId);
        assertThat(findUpdateTag.getName()).isEqualTo(updateName);
    }

    /**
     * 삭제 - tag 한 건(단건 삭제)
     */
    @Test
    @DisplayName("TagService.deleteOneTag() 테스트 (단건 삭제)")
    public void deleteOneTag() throws Exception {
        //given
        TagRequestDto tagRequestDto = TagRequestDto.builder()
                .name("Spring")
                .build();
        Long tagId = tagService.postOneTag(tagRequestDto);
        long beforeDeleteCnt = tagRepository.count();
        //when
        Long deleteId = tagService.deleteOneTag(tagId);
        //then
        assertThat(tagRepository.count()).isEqualTo(beforeDeleteCnt-1);
        assertThat(tagRepository.findById(deleteId)).isEqualTo(Optional.empty());
    }

    /**
     * 조회 - tag 전체 (페이징 x)
     */
    @Test
    @DisplayName("TagService.getAllTag() 테스트 (전체 조회)")
    public void getAllTag() throws Exception {
        //given
        long beforeTestCnt = tagRepository.count();
        int targetIdx = (int) beforeTestCnt;

        Tag tag1 = Tag.builder().name("Spring").build();
        Tag tag2 = Tag.builder().name("HTML").build();
        Tag tag3 = Tag.builder().name("CSS").build();

        tagRepository.save(tag1);
        tagRepository.save(tag2);
        tagRepository.save(tag3);
        //when
        List<TagResponseDto> allTag = tagService.getAllTag();
        //then
        assertThat(allTag.size()).isEqualTo(beforeTestCnt+3);  //데이터베이스에 값이 있을경우를 대비
        assertThat(allTag.get(targetIdx).getId()).isEqualTo(tag1.getId());
        assertThat(allTag.get(targetIdx).getName()).isEqualTo(tag1.getName());
    }

}

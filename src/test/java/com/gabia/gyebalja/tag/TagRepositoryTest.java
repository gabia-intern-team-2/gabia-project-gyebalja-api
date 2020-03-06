package com.gabia.gyebalja.tag;

import com.gabia.gyebalja.domain.Tag;
import com.gabia.gyebalja.repository.TagRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@DataJpaTest
public class TagRepositoryTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    TagRepository tagRepository;

    @Test
    @DisplayName("Tag 저장 테스트(save)")
    public void saveTest() throws Exception {
        //given
        long beforeCnt = tagRepository.count();

        Tag tag = Tag.builder()
                    .name("#Spring")
                    .build();

        //when
        tagRepository.save(tag);
        em.clear();

        Tag findTag = tagRepository.findById(tag.getId()).get();

        //then
        assertThat(findTag.getId()).isEqualTo(tag.getId());
        assertThat(findTag.getName()).isEqualTo(tag.getName());
        assertThat(tagRepository.count()).isEqualTo(beforeCnt+1);

    }

    @Test
    @DisplayName("Tag 단건조회 테스트(findById)")
    public void findByIdTest() throws Exception {
        //given
        Tag tag1 = Tag.builder()
                .name("#Spring")
                .build();
        tagRepository.save(tag1);

        Tag tag2 = Tag.builder()
                .name("#JPA")
                .build();
        tagRepository.save(tag2);

        em.clear();

        //when
        Tag findTag = tagRepository.findById(tag2.getId()).get();

        //then
        assertThat(findTag.getId()).isEqualTo(tag2.getId());
        assertThat(findTag.getName()).isEqualTo(tag2.getName());

    }

    @Test
    @DisplayName("Tag 전체 조회 테스트(findAll)")
    public void findAllTest() throws Exception {
        //given
        Tag tag1 = Tag.builder()
                .name("#Spring")
                .build();
        tagRepository.save(tag1);

        Tag tag2 = Tag.builder()
                .name("#JPA")
                .build();
        tagRepository.save(tag2);

        em.clear();

        //when
        List<Tag> allTag = tagRepository.findAll();

        //then
        assertThat(allTag.size()).isEqualTo(2);
        assertThat(allTag.get(0).getId()).isEqualTo(tag1.getId());
        assertThat(allTag.get(0).getName()).isEqualTo(tag1.getName());

    }

    @Test
    @DisplayName("Tag 갯수 테스트(count)")
    public void countTest() throws Exception {
        //given
        Tag tag1 = Tag.builder()
                .name("#Spring")
                .build();
        tagRepository.save(tag1);

        Tag tag2 = Tag.builder()
                .name("#JPA")
                .build();
        tagRepository.save(tag2);

        //when
        long count = tagRepository.count();

        //then
        assertThat(count).isEqualTo(2);

    }

    @Test
    @DisplayName("Tag 삭제 테스트(delete)")
    public void deleteTest() throws Exception {
        //given
        Tag tag1 = Tag.builder()
                .name("#Spring")
                .build();
        tagRepository.save(tag1);

        Tag tag2 = Tag.builder()
                .name("#JPA")
                .build();
        tagRepository.save(tag2);

        long beforeDeleteCnt = tagRepository.count();

        //when
        tagRepository.delete(tag1);

        //then
        assertThat(tagRepository.count()).isEqualTo(beforeDeleteCnt-1);
        assertThat(tagRepository.findById(tag1.getId())).isEqualTo(Optional.empty());

    }

    @Test
    @DisplayName("Tag 업데이트 테스트(update)")
    public void updateTest() throws Exception {
        //given
        String updateName = "이름 업데이트";

        Tag tag = Tag.builder()
                    .name("#Spring")
                    .build();
        tagRepository.save(tag);
        long beforeUpdateCnt = tagRepository.count();

        //when
        tag.changeTagName(updateName);
        Tag findTag = tagRepository.findById(tag.getId()).get();

        //then
        assertThat(findTag.getId()).isEqualTo(tag.getId());
        assertThat(findTag.getName()).isEqualTo(updateName);
        assertThat(tagRepository.count()).isEqualTo(beforeUpdateCnt);

    }

    @Test
    @DisplayName("Tag 이름으로 조회 테스트(findHashTagByName)")
    public void findHashTagByName() throws Exception {
        //given
        String hashTagName  = "#Spring";
        Tag tag = Tag.builder()
                .name(hashTagName)
                .build();
        Tag savedTag = tagRepository.save(tag);

        //when
        Tag findTagByName = tagRepository.findHashTagByName(hashTagName).get();

        //then
        assertThat(savedTag.getId()).isEqualTo(findTagByName.getId());

    }

}

package com.gabia.gyebalja.comment;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@Rollback(true)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CommentTest {
//    @Autowired
//    CommentRepository commentRepository;
//    @Autowired
//    CommentService commentService;
//    @Autowired
//    TestRestTemplate restTemplate;
//    @LocalServerPort
//    private int port;
//
//    /** 등록 - comment 한 건 */
//    @Test
//    public void postOneComment(){
//        //given
//        String content = "댓글 작성";
//        String url = "http://localhost:" + port + "/api/v1/comments";
//
//        GenderType GenderType = GenderType.builder().content(content).build();
//
//        //when
//        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, GenderType, Long.class);
//
//        //then
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(responseEntity.getBody()).isGreaterThan(0L);
//        Comment comment = commentRepository.findById(responseEntity.getBody()).orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다."));
//        assertThat(comment.getContent()).isEqualTo(content);
//    }
//
//    /** 조회 - comment 한 건 */
//    @Test
//    public void getOneComment(){
//        //given
//        Long targetId = 1L;
//        String url = "http://localhost:" + port + "/api/v1/comments/" + targetId;
//
//        Comment comment = commentRepository.findById(targetId).orElseThrow(()->new IllegalArgumentException("해당 댓글이 없습니다."));
//        GenderType GenderType = new GenderType(comment);
//
//        //when
//        ResponseEntity<GenderType> responseEntity = restTemplate.getForEntity(url, GenderType.class);
//
//        //then
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(responseEntity.getBody().getContent()).isEqualTo(GenderType.getContent());
//    }
//
//    /** 수정 - comment 한 건
//     *  새 데이터 삽입 후 내용 변경하여 수정 잘 되었는지 테스트 진행
//     * */
//    @Test
//    public void putOneComment(){
//        //given
//        Comment savedComment = commentRepository.save(Comment.builder().content("content").build());
//        Long updateId = savedComment.getId();
//        String updateContent = "updated content";
//        String url = "http://localhost:" + port + "/api/v1/comments/" + updateId;
//
//        GenderType GenderType = GenderType.builder().content(updateContent).build();
//        HttpEntity<GenderType> requestEntity = new HttpEntity<>(GenderType);
//
//        //when
//        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);
//
//        //then
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(responseEntity.getBody()).isGreaterThan(0L);
//        Comment comment = commentRepository.findById(updateId).orElseThrow(()->new IllegalArgumentException("해당 댓글이 없습니다."));
//        assertThat(comment.getContent()).isEqualTo(updateContent);
//    }
//
//    /** 삭제 - comment 한 건
//     *  데이터 개수로 테스트 진행 (삽입 전 개수와 삽입, 삭제 후 개수 동일 체크)
//     * */
//    @Test
//    public void deleteOneComment(){
//        //given
//        long totalCount = commentRepository.count();
//        Comment savedComment = commentRepository.save(Comment.builder().content("content").build());
//        Long deleteId = savedComment.getId();
//        String url = "http://localhost:" + port + "/api/v1/comments/" + deleteId;
//
//        HttpHeaders headers = new HttpHeaders();
//        HttpEntity requestEntity = new HttpEntity(headers);
//
//        //when
//        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, Long.class); // 검토. 왜 select ?
//
//        //then
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(responseEntity.getBody()).isGreaterThan(0L);
//        assertThat(commentRepository.count()).isEqualTo(totalCount);
//    }
//
//    /** 쿼리메소드 테스트 findByBoardId() */
//    @Test
//    public void findByBoardIdTest(){
//        // given
//        Long targetId = 1L;
//        List<Comment> commentList = new ArrayList<Comment>();
//
//        // when
//        try {
//            commentList = commentRepository.findByBoardId(targetId);
//        }catch (Exception e) {
//            System.out.println(e);
//        }
//
//        // then
//        System.out.println(commentList.toString());
//    }
}

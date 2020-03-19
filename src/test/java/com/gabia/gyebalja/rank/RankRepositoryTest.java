package com.gabia.gyebalja.rank;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * Author : 정태균
 * Part : All
 */

@Transactional
@DataJpaTest(properties = "spring.config.location=classpath:application-test.yml")
public class RankRepositoryTest {
}

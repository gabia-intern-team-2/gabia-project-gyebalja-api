package com.gabia.gyebalja.service.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 참고
 * https://meetup.toast.com/posts/149
 * https://www.sangkon.com/hands-on-springboot-logging/
 * */

@Service
public class LogService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    public void log() {
        logger.trace("Gyebalja-Trace");
        logger.debug("Gyebalja-Debug");
        logger.info("Gyebalja-Info");
        logger.warn("Gyebalja-Warn");
        logger.error("Gyebalja-Error");
    }
}

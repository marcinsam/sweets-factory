package pl.marboz.myproject.messaging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * Created by Marcin Bozek on 2016-02-20.
 */
@Component
public class Receiver {

    private static final Logger log = LogManager.getLogger(Receiver.class);

    public void handleMessage(String message) {
        log.info(new StringBuilder("Received <").append(message).append(">"));
    }
}

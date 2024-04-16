package xyz.needpainkiller.lib.sse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@Component
public class SseEmitters {

    public static final long SSE_SESSION_TIMEOUT = 30 * 1000L; // 30 sec
    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>(); // Thread-Safe List

    public SseEmitter add(SseEmitter emitter) {
        this.emitters.add(emitter);
        log.info("new emitter added: {}", emitter);
        log.info("emitter list size: {}", emitters.size());
        emitter.onCompletion(() -> {
            log.info("onCompletion callback");
            this.emitters.remove(emitter);    // 만료되면 리스트에서 삭제
        });
        emitter.onTimeout(() -> {
            log.info("onTimeout callback");
            emitter.complete();
        });

        return emitter;
    }

    public boolean isEmpty() {
        return emitters.isEmpty();
    }

    public void sendAll(String name, Serializable data) {
        log.error("sendAll  : {} - {}", name, data);
        emitters.forEach(emitter -> {
            try {
                emitter.send(SseEmitter.event()
                        .name(name)
                        .data(data));
            } catch (IOException e) {
                log.error("sendAll Error : {}", e.getMessage());
            }
        });
    }
}

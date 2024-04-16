package xyz.needpainkiller.base.bootstrap;

import jakarta.annotation.PostConstruct;
import org.springframework.transaction.annotation.Transactional;

public interface Bootstrap {
    @PostConstruct
    @Transactional
    void bootstrap();
}

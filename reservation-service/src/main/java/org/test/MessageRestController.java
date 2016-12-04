package org.test;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Philippe
 */
@RefreshScope
@RestController
public class MessageRestController {

    @Value("${message}")
    private String message;

    @GetMapping("/message")
    String message() {
        return this.message;
    }
}

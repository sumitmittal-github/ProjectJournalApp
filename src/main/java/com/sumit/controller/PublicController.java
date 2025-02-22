package com.sumit.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/public")
@Log4j2
public class PublicController {

    @GetMapping("/health-check")
    public ResponseEntity<String> healthCheck() {
        log.trace("Trace");
        log.debug("Debug");
        log.info("Info");
        log.warn("Warn");
        log.error("Error");
        log.fatal("Fatal");
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

}
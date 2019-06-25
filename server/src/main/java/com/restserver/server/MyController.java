package com.restserver.server;

import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MyController {

    @Async
    @PostMapping
    Example post(@RequestBody Example ex){
        return ex;
    }


}

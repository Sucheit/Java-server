package ru.myapp.controllers.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.myapp.grpc.GrpcClient;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/grpc")
public class GrpcController {

    private final GrpcClient grpcClient;

    @PostMapping(path = "/{name}")
    public String getGreeting(@PathVariable(name = "name") String name) {
        return grpcClient.greeting(name);
    }
}

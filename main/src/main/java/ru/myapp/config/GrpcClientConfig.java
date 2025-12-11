package ru.myapp.config;

import io.grpc.Channel;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.arthur.inzhilov.grpc.stubs.HelloWorldServiceGrpc;

@Configuration
public class GrpcClientConfig {

  @Bean
  public HelloWorldServiceGrpc.HelloWorldServiceBlockingStub exampleServiceBlockingStub(
      @GrpcClient("greeting") Channel channel) {
    return HelloWorldServiceGrpc.newBlockingStub(channel);
  }
}
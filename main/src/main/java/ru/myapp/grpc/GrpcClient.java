package ru.myapp.grpc;

import io.grpc.StatusRuntimeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.arthur.inzhilov.grpc.stubs.GreetingRequest;
import ru.arthur.inzhilov.grpc.stubs.GreetingResponse;
import ru.arthur.inzhilov.grpc.stubs.HelloWorldServiceGrpc;

@Slf4j
@Service
@RequiredArgsConstructor
public class GrpcClient {

  private final HelloWorldServiceGrpc.HelloWorldServiceBlockingStub stub;

  public String greeting(String request) {
    try {
      GreetingRequest greetingRequest = GreetingRequest.newBuilder()
          .setGreeting(request)
          .build();
      GreetingResponse grpcResponse = stub.greeting(greetingRequest);

      return grpcResponse.getResponse();
    } catch (StatusRuntimeException e) {
      log.error("gRPC error: ", e);
      throw new RuntimeException("gRPC call failed: " + e.getStatus());
    }
  }
}

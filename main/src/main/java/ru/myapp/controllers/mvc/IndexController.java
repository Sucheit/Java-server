package ru.myapp.controllers.mvc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

  @Operation(summary = "Redirect to swagger-ui page")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Index page redirected")})
  @GetMapping(path = "/")
  public void index(HttpServletResponse response) throws IOException {
    response.sendRedirect("/swagger-ui/index.html");
  }
}


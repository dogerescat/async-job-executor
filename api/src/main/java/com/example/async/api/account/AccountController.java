package com.example.async.api.account;

import jakarta.validation.Valid;
import com.example.async.api.application.account.AccountService;
import com.example.async.api.application.account.CreateAccountCommand;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
public class AccountController {
  private final AccountService accountService;

  public AccountController(AccountService accountService) {
    this.accountService = accountService;
  }

  @PostMapping
  public ResponseEntity<CreateAccountResponse> create(
      @Valid @RequestBody CreateAccountRequest request) {
    var command = new CreateAccountCommand(request.name(), request.email(), request.password());
    var result = accountService.create(command);
    return ResponseEntity.ok(new CreateAccountResponse(
        "Hello, " + result.getName().getValue() + "! Your account has been created."));
  }
}

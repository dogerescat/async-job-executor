package com.example.async.api.account;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateAccountRequest(@NotBlank @Size(max = 16) String name, @NotBlank String email,
    @NotBlank String password) {

}

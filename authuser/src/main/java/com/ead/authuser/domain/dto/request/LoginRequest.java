package com.ead.authuser.domain.dto.request;

import javax.validation.constraints.NotBlank;

public record LoginRequest(@NotBlank String username,
                           @NotBlank String password) {
}

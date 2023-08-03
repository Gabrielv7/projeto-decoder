package com.ead.authuser.domain.dto.response;

import lombok.NonNull;

public record JwtResponse(@NonNull String token,
                          @NonNull String type) {
}

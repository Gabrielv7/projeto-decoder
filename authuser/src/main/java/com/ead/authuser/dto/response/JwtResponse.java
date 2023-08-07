package com.ead.authuser.dto.response;

import lombok.NonNull;

public record JwtResponse(@NonNull String token,
                          @NonNull String type) {
}

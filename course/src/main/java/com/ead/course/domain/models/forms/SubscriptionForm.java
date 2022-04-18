package com.ead.course.domain.models.forms;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class SubscriptionForm {

    @NotNull
    private UUID userId;

}

package com.ead.authuser.domain.model.forms;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class InstructorForm {

    @NotNull
    private UUID userId;

}

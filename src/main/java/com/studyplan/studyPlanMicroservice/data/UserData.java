package com.studyplan.studyPlanMicroservice.data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserData {
    private Integer idUser;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    @NotNull(message = "Registration date is required")
    private LocalDateTime dateRegister;

    private LocalDateTime datePurchase;

    @NotBlank(message = "Type is required")
    private String type;
}

package com.gratiStore.api_gratiStore.domain.validator.pontoEletronico;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = HorariosCoerentesValidator.class)
@Documented
public @interface HorariosCoerentes {

    String message() default "Se um dos horários foi preenchido, todos devem ser preenchidos";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

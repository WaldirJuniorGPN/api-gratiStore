package com.gratiStore.api_gratiStore.controller.dto.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.Duration;

public class DurationSerializer extends JsonSerializer<Duration> {

    @Override
    public void serialize(Duration value, JsonGenerator jsonGenerator, SerializerProvider serializer) throws IOException {

        if (value == null) {
            jsonGenerator.writeNull();
            return;
        }

        var segundos = value.getSeconds();
        var negativo = segundos < 0;
        var absSegundos = Math.abs(segundos);
        var horas = absSegundos / 3600;
        var minutos = (absSegundos % 3600) / 60;
        var formatacao = String.format(("%s%02d:%02d"), negativo ? "- " : "", horas, minutos);
        jsonGenerator.writeString(formatacao);
    }
}

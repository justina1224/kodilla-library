package com.crud.kodillalibrary.jsonformatting;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDate;

import static com.crud.kodillalibrary.KodillaLibraryApplication.FORMATTER;


public class LocalDateSerializer extends JsonSerializer<LocalDate> {

    @Override
    public void serialize(LocalDate value, JsonGenerator generator, SerializerProvider serializers) throws IOException {
        generator.writeString(value.format(FORMATTER));
    }
}

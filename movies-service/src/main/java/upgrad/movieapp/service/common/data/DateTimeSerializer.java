/*
 * Copyright 2018-2019, https://beingtechie.io.
 *
 * File: DateTimeSerializer.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package upgrad.movieapp.service.common.data;

import java.io.IOException;
import java.time.ZonedDateTime;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * Custom json deserializer for {@link ZonedDateTime} objects.
 */
public class DateTimeSerializer extends JsonSerializer<ZonedDateTime> {

    @Override
    public void serialize(ZonedDateTime value, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException, JsonProcessingException {
        jsonGenerator.writeObject(value.toInstant().toEpochMilli());
    }

}
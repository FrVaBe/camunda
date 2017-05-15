package de.frvabe.bpm.camunda.spin.demo.json;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;


import de.frvabe.bpm.camunda.spin.demo.pojo.ImmutablePersons;
import spinjar.com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Serialization tests that use Jackson for JSON serialization.
 */
public class JsonJacksonSerializationTest {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void writeAndReadAsJson() throws IOException {

        // given

        ImmutablePersons persons = PersonsFactory.createImmutablePersons();

        // when

        String personsAsJson = mapper.writeValueAsString(persons);
        ImmutablePersons unmarshal = mapper.readValue(personsAsJson, ImmutablePersons.class);

        // then

        assertEquals(unmarshal.getItems().get(0), persons.getItems().get(0));
        assertEquals(unmarshal.getItems().get(1), persons.getItems().get(1));

    }

}

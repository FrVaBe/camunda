package de.frvabe.bpm.camunda.spin.demo.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Simple PoJo to wrap a list of {@link ImmutablePerson} objects.
 * <p>
 * Implemented as an immutable object; thus it has a full value constructor and getters but no
 * setters.
 */
public class ImmutablePersons implements Serializable {

    private static final long serialVersionUID = 1L;

    private final List<ImmutablePerson> items;

    /**
     * Full value constructor.
     * 
     * @param items the list of persons
     */
    @JsonCreator
    public ImmutablePersons(@JsonProperty("items") final List<ImmutablePerson> items) {
        this.items = items == null ? new ArrayList<>() : items;
    }

    public List<ImmutablePerson> getItems() {
        return items;
    }

}

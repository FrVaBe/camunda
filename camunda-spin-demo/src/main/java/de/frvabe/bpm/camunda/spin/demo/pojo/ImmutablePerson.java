package de.frvabe.bpm.camunda.spin.demo.pojo;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Simple person PoJo.
 * <p>
 * Implemented as an immutable object; thus it has a full value constructor and getters but no
 * setters.
 */
public class ImmutablePerson implements Serializable {

    private static final long serialVersionUID = 1L;

    private String firstName;
    private String lastName;

    /**
     * Full value constructor.
     * 
     * @param firstName the first name
     * @param lastName the last name
     */
    @JsonCreator
    public ImmutablePerson(@JsonProperty("firstName") final String firstName,
            @JsonProperty("lastName") final String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof ImmutablePerson)) {
            return false;
        }
        ImmutablePerson other = (ImmutablePerson) obj;
        return Objects.equals(firstName, other.firstName)
                && Objects.equals(lastName, other.lastName);
    }

}

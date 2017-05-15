package de.frvabe.bpm.camunda.spin.demo.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple PoJo to wrap a list of {@link Person} objects.
 */
public class Persons implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Person> items;

    public List<Person> getItems() {
        if (items == null) {
            items = new ArrayList<>();
        }
        return items;
    }
    
    public void setItems(final List<Person> items) {
        this.items = items;
    }

}

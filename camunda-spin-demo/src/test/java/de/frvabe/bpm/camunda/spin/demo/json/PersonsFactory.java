package de.frvabe.bpm.camunda.spin.demo.json;

import java.util.ArrayList;
import java.util.List;

import de.frvabe.bpm.camunda.spin.demo.pojo.ImmutablePerson;
import de.frvabe.bpm.camunda.spin.demo.pojo.ImmutablePersons;
import de.frvabe.bpm.camunda.spin.demo.pojo.Person;
import de.frvabe.bpm.camunda.spin.demo.pojo.Persons;

/**
 * {@link Persons}/{@link ImmutablePersons} factory to provide consistent test and demo data.
 */
public final class PersonsFactory {

    private PersonsFactory() {
        // hide constructor
    }

    /**
     * Creates aa {@link ImmutablePersons} object with some {@ImmutablePerson}s in the items list.
     * 
     * @return an ImmutablePersons object; can be used for demo and testing
     */
    public static ImmutablePersons createImmutablePersons() {
        ImmutablePerson p1 = new ImmutablePerson("Max", "Mustermann");
        ImmutablePerson p2 = new ImmutablePerson("Erika", "Mustermann");
        List<ImmutablePerson> items = new ArrayList<>();
        items.add(p1);
        items.add(p2);
        return new ImmutablePersons(items);
    }

    /**
     * Creates a {@link Persons} object with some {@Person}s in the items list.
     * 
     * @return a Persons object; can be used for demo and testing
     */
    public static Persons createPersons() {
        Person p1 = new Person();
        p1.setFirstName("Max");
        p1.setLastName("Mustermann");
        Person p2 = new Person();
        p2.setFirstName("Erika");
        p2.setLastName("Mustermann");
        Persons persons = new Persons();
        persons.getItems().add(p1);
        persons.getItems().add(p2);
        return persons;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import com.google.gson.Gson;
import exceptions.NotFoundException;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import model.Person;

/**
 *
 * @author Henrik
 */
public class PersonFacadeDB implements IPersonFacade {

    private static PersonFacadeDB instance = new PersonFacadeDB();

    public static PersonFacadeDB getFacade(boolean b) {

        if (true) {
            instance = new PersonFacadeDB();
        }
        return instance;

    }
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("RestCRUDPU");
    EntityManager em = emf.createEntityManager();
    private final Gson gson = new Gson();

    @Override
    public Person addPerson(String json) {
        Person p = gson.fromJson(json, Person.class);
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();

        return p;

    }

    @Override
    public Person deletePerson(int id) throws NotFoundException {
        Person p = em.find(Person.class, id);
        if (p == null) {
            throw new NotFoundException("No person exists for the given id");
        }

        em.getTransaction().begin();
        em.remove(p);
        em.getTransaction().commit();

        return p;
    }

    @Override
    public String getPerson(int id) throws NotFoundException {
        Person p = em.find(Person.class, id);
         if (p == null) {
            throw new NotFoundException("No person exists for the given id");
        }
        return gson.toJson(p);
    }

    @Override
    public String getPersons() {
        Query p = em.createQuery("SELECT p FROM Person p");

        List pe = p.getResultList();

        System.out.println(pe);

        Collections.reverse(pe);

        return gson.toJson(pe);
    }

    @Override
    public Person editPerson(String json) throws NotFoundException {
        Person p = gson.fromJson(json, Person.class);
        Person oldValue = em.find(Person.class, p.getId());
        if (oldValue == null) {
            throw new NotFoundException("No person exists for the given id");
        }

        em.persist(p);
        return oldValue;
    }

}

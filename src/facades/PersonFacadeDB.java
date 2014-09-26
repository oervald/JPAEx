/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import com.google.gson.Gson;
import exceptions.NotFoundException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import model.Person;

/**
 *
 * @author Henrik
 */
public class PersonFacadeDB implements IPersonFacade {

    private static PersonFacadeDB instance = new PersonFacadeDB();

    public static PersonFacadeDB getFacade(boolean b) {
        
    if(true){
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
        
        System.out.println(p.getPhone());
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();
        return p;

    }

    @Override
    public Person deletePerson(int id) throws NotFoundException {
        Person p = em.find(Person.class, id);
        em.getTransaction().begin();
        em.remove(p);
        em.getTransaction().commit();
        return p;
    }

    @Override
    public String getPerson(int id) throws NotFoundException {
        Person p = em.find(Person.class, id);
        return p.toString();
    }

    @Override
    public String getPersons() {
        String p = em.createQuery("SELECT p FROM Person p").toString();

        return p;
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Henrik
 */
public class OOCreate {

  
    public void test() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("RestCRUDPU");
        EntityManager em = emf.createEntityManager();
        Person p = new Person();
        
        //Kald der instantiere table på DB for Person
        em.persist(p);
                
        System.out.println(p);
        
        
    }

    public static void main(String[] args) {
        // TODO code application logic here
        new OOCreate().test();

    }

}

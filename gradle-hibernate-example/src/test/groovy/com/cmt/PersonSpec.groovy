package com.cmt

import spock.lang.*
import org.hibernate.Session
import org.hibernate.SessionFactory
import org.hibernate.boot.MetadataSources
import org.hibernate.boot.registry.StandardServiceRegistry
import org.hibernate.boot.registry.StandardServiceRegistryBuilder

class PersonSpec extends Specification {

    static SessionFactory sessionFactory

    void setupSpec() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build()

        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory()
        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry)
        }
    }

    void cleanupSpec() {
        if (sessionFactory != null) {
            sessionFactory.close()
        }
    }

    void "Test people can be saved and queried"() {
        setup:
            Session session = sessionFactory.openSession()
            session.beginTransaction()
            session.save( new Person(name: 'Luke Skywalker', age: 18) )
            session.save( new Person(name: 'Han Solo', age: 32) )
            session.getTransaction().commit()
            session.close()

        when:
            session = sessionFactory.openSession()
            session.beginTransaction()
            List result = session.createQuery( "from Person" ).list()
            session.getTransaction().commit()
            session.close()

        then:
            result.size() == 2
    }

}

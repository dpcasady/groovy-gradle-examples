package com.cmt

import spock.lang.*
import grails.gorm.DetachedCriteria
import grails.orm.bootstrap.*
import org.springframework.jdbc.datasource.DriverManagerDataSource

class PersonSpec extends Specification {

    static def dataSource
    static def applicationContext

    void setupSpec() {
        def init = new HibernateDatastoreSpringInitializer(Person)
        dataSource = new DriverManagerDataSource("jdbc:h2:mem:gorm;DB_CLOSE_DELAY=-1", "sa", "")
        applicationContext = init.configureForDataSource(dataSource)
    }

    void "Test the database schema is populated correctly"() {
        when:"The dataSource is configured"
            def conn = dataSource.getConnection()

        then:"The database tables are created correctly"
            conn.prepareStatement("SELECT * FROM PERSON").execute()
    }

    void "Test GORM is initialized correctly"() {
        when:"A GORM method is invoked"
            def total = Person.withNewSession { Person.count() }

        then:"The correct results are returned"
            total == 0
    }

    void "Test person is persisted correctly"() {
        when:"A new domain instance is created"
            def person = new Person()

        then:"It is initially invalid"
            !Person.withNewSession { person.validate() }

        when:"It is made valid"
            person.name = "Luke Skywalker"
            person.age = 18

        then:"It can be saved"
            Person.withNewSession { person.save() }
            Person.withNewSession { Person.count() } == 1
    }

    void "Test person is queried correctly"() {
        when:
            DetachedCriteria<Person> query = Person.where {
                name == "Han Solo"
            }
            def result = Person.withNewSession { query.find() }
        then:
            result == null

        when:
            query = Person.where {
                name == "Luke Skywalker"
            }
            result = Person.withNewSession { query.find() }
        then:
            result instanceof Person
    }

    @Unroll
    void "Test age constraint"(Integer age, boolean isValidated) {
        setup:
            Person person = new Person(
                age: age,
                name: "Luke Skywalker"
            )

        expect:
            Person.withNewSession { person.validate() } == isValidated

        where:
            age | isValidated
            10  | false
            18  | true
            30  | true
            100 | false
    }

}

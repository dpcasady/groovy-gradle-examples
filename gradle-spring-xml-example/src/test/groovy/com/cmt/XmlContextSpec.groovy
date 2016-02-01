package com.cmt

import org.springframework.context.ApplicationContext
import org.springframework.context.support.ClassPathXmlApplicationContext
import spock.lang.*

class XmlContextSpec extends Specification {

    void "Test beans can be retrived from the XML application context"() {
        setup:
            ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");

        when:
            Person knight = (Person) context.getBean("jedi")

        then:
            knight.theForce.isStrong == true
            knight.home.name == "Tatooine"

        when:
            Person stormtrooper = (Person) context.getBean("stormtrooper")

        then:
            stormtrooper.theForce.isStrong == false
            stormtrooper.home.name == "Tatooine"
    }

}

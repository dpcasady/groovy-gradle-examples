package com.cmt

import grails.persistence.*

/**
 * The @Entity annotation triggers an AST transformation used to make a
 * class into a GORM domain class.
 *
 * The AST transformation adds the following features:
 *
 * - An id and version (if not already present)
 * - A toString() method (if not already present)
 * - Associations and association methods (addTo*, removeFrom*) etc.
 * - Association id getter methods ('userId' for 'user' association)
 * - Adds the GormEntity and GormValidateable traits
 * - Named query methods
 */
@Entity
class Person {

    String name
    Integer age

    static constraints = {
        age min: 18, max: 65
    }

}

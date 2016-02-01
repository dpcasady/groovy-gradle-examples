package com.cmt

class Person {

    Force theForce
    Planet home

    /**
     * Since we're declaring a parameterized constructor, the compiler
     * doesn’t generate the default empty one so we need to declare it
     * here.
     */
    Person() { }

    Person(Force theForce) {
        this.theForce = theForce
    }

}

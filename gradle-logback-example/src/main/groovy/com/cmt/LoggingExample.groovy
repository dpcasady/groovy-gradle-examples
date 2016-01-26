package com.cmt

import groovy.util.logging.Slf4j

/**
 * Use @Slf4j annotation to insert a 'log' field into the class.
 */
@Slf4j
class LoggingExample {

    static void main(String... args) {
        1.upto(300) {
            sleep(200)
            log.trace 'This is trace'
            log.debug 'This is debug'
            log.info 'This is info'
            log.warn 'This is warn'
            log.error 'This is error'
        }
    }

}

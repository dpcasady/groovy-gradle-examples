import ch.qos.logback.classic.filter.ThresholdFilter

// See http://logback.qos.ch/manual/groovy.html for details on configuration

def targetDir = "logs"

/**
 * Define a basic console appender named 'STDOUT'. Note the
 * ThresholdFilter which will filter out all logs below the ERROR level.
 */
appender('STDOUT', ConsoleAppender) {
    filter(ThresholdFilter) {
        level = ERROR
    }
    encoder(PatternLayoutEncoder) {
        pattern = "%d{yyyy-MM-dd} %d{HH:mm:ss.SSS} %level %logger - %msg%n"
    }
}

/**
 * Define a RollingFileAppender called 'SIZE_FILE' for a file named
 * 'rolling-size.log' that rolls over to 'rolling-size.1.log',
 * 'rolling-size.2.log', etc. after the initial log file exceeds 5MB
 * in size. Each file that has rolled over will automatically be gzipped
 * due to the file name pattern ending in .gz. No more than 5 log files
 * will be kept.
 */
appender("SIZE_FILE", RollingFileAppender) {
    file = "${targetDir}/rolling-size.log"
    rollingPolicy(FixedWindowRollingPolicy) {
        fileNamePattern = "${targetDir}/rolling-size.%i.log.gz"
        minIndex = 1
        maxIndex = 5
    }
    triggeringPolicy(SizeBasedTriggeringPolicy) {
        maxFileSize = "5KB"
    }
    encoder(PatternLayoutEncoder) {
        pattern = "%d{yyyy-MM-dd} %d{HH:mm:ss.SSS} %level %logger - %msg%n"
    }
}

/**
 * Define a RollingFileAppender called 'TIME_FILE' for a file named
 * 'rolling-time.log' that rolls over to 'rolling-time.2016-01-01 12:00.log',
 * 'rolling-time.2016-01-01 12:01.log', etc. at the start of every
 * minute. TimeBasedRollingPolicy assumes responsibility for both the
 * rollover itself as well as the triggering of it, where the triggering
 * is inferred by the file name pattern. No more than 5 log files will
 * be kept.
 */
appender("TIME_FILE", RollingFileAppender) {
    file = "${targetDir}/rolling-time.log"
    rollingPolicy(TimeBasedRollingPolicy) {
        fileNamePattern = "${targetDir}/rolling-time.%d{yyyy-MM-dd_HH-mm}.log"
        maxHistory = 5
    }
    encoder(PatternLayoutEncoder) {
        pattern = "%d{yyyy-MM-dd} %d{HH:mm:ss.SSS} %level %logger - %msg%n"
    }
}

/**
 * Define loggers
 */
root(ERROR, ['STDOUT'])
logger("com.cmt", TRACE, ["SIZE_FILE"])
logger("com.cmt", DEBUG, ["TIME_FILE"])

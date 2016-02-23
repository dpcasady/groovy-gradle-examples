package com.cmt

import javax.mail.*
import javax.mail.internet.*

class MailExample {

    static void main(String... args) {

        try {
            // TODO: Define a valid gmail account you have access to
            final String fromEmail = "user@gmail.com"
            final String password = "password"
            final String toEmail = "user@host.com"

            String subject = "David Copperfield"
            String text = "Whether I shall turn out to be the hero of my own life, or whether that station will be held by anybody else, these pages must show."

            // Set up properties defined by the JavaMail API to pass to the mail Session
            // See https://javamail.java.net/nonav/docs/api/
            def props = new Properties()
            props.put("mail.smtp.host", "smtp.gmail.com")
            props.put("mail.smtp.port", "587")
            props.put("mail.smtp.auth", "true")
            props.put("mail.smtp.starttls.enable", "true")
            props.put("mail.smtp.debug", "true")

            // Create Authenticator object to pass to the mail Session
            def auth = new Authenticator() {
                // override the getPasswordAuthentication method
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, password)
                }
            }
            def session = Session.getInstance(props, auth)
            session.setDebug(true)

            def message = new MimeMessage(session)
            message.setFrom(new InternetAddress(fromEmail))
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail))
            message.setSubject(subject)
            message.setText(text)

            Transport.send(message)
        } catch(Exception e) {
            println "There was a problem sending the email"
        }
    }

}

package com.cmt

import java.util.Properties
import javax.mail.*
import javax.mail.internet.*
import javax.activation.*
import groovy.util.logging.Slf4j

@Slf4j
class MailSender {

    boolean isDebug = false

    /**
     * Send a text-only SMTP MIME email message, or if an attachment is
     * present, send a multipart SMTP MIME email message.
     */
    def send(MailMessage msg, String password) {
        try {
            def session = setupSession(msg, password)
            def message = new MimeMessage(session)

            message.setFrom(new InternetAddress(msg.from))
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(msg.to))
            message.setSubject(msg.subject)

            if (msg.attachment) {
                def messageBodyPart = new MimeBodyPart()
                messageBodyPart.setText(msg.text)

                def multipart = new MimeMultipart()
                multipart.addBodyPart(messageBodyPart)

                messageBodyPart = new MimeBodyPart()
                def source = new FileDataSource(msg.attachment)
                messageBodyPart.setDataHandler(new DataHandler(source))
                messageBodyPart.setFileName(msg.attachment.name)
                multipart.addBodyPart(messageBodyPart)

                message.setContent(multipart)
            } else {
                message.setText(msg.text)
            }

            Transport.send(message)
        } catch(Exception e) {
            log.error "There was a problem sending the email", e
        }
    }

    /**
     * Configure a new mail Session that contains JavaMail properties
     * and knows how to authenticate against an SMTP server.
     */
    Session setupSession(MailMessage msg, String password) {
        // Create Authenticator object to pass to the mail Session
        def auth = new Authenticator() {
            // override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(msg.from, password)
            }
        }
        def session = Session.getInstance(setupProperties(), auth)
        session.setDebug(isDebug)

        return session
    }

    /**
     * Set up SMTP properties defined by the JavaMail API to pass to the
     * mail Session.
     *
     * See https://javamail.java.net/nonav/docs/api/
     */
    Properties setupProperties() {
        def props = new Properties()
        props.put("mail.smtp.host", "smtp.gmail.com")
        props.put("mail.smtp.port", "587")
        props.put("mail.smtp.auth", "true")
        props.put("mail.smtp.starttls.enable", "true")
        if (isDebug) {
            props.put("mail.smtp.debug", "true")
        }

        return props
    }

}

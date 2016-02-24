package com.cmt

import javax.mail.*
import javax.mail.internet.*

class MailExample {

    static final String UTF8 = 'UTF-8'

    static void main(String... args) {
        // TODO: Define a valid gmail account you have access to
        String toEmail = "user@host.com"
        String fromEmail = "user@gmail.com"
        String password = "password"

        def sender = new MailSender(isDebug: true)

        // Send a plain text email
        String subject = "David Copperfield"
        String text = "Whether I shall turn out to be the hero of my own life, or whether that station will be held by anybody else, these pages must show."

        def message = new MailMessage(to: toEmail, from: fromEmail, subject: subject, text: text)
        sender.send(message, password)

        // Send an email with an attachment
        subject = "Slaughterhouse-Five"
        text = "All this happened, more or less."

        def url = MailMessage.getClassLoader().getResource("Vonnegut.txt")
        def attachment = new File(url.toURI())

        message = new MailMessage(to: toEmail, from: fromEmail, subject: subject, text: text, attachment: attachment)
        sender.send(message, password)
    }

}

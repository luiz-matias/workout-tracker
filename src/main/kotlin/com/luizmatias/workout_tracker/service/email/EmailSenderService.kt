package com.luizmatias.workout_tracker.service.email

import com.luizmatias.workout_tracker.config.exception.common_exceptions.InternalServerErrorException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class EmailSenderService @Autowired constructor(
    private val mailgunEmailSender: MailgunEmailSender,
    private val sendGridEmailSender: SendGridEmailSender
) : NotificationSenderService {

    override fun send(to: String, subject: String, body: String) {
        try {
            mailgunEmailSender.send(to, subject, body)
        } catch (e: InternalServerErrorException) {
            try {
                sendGridEmailSender.send(to, subject, body)
            } catch (e: InternalServerErrorException) {
                throw InternalServerErrorException("Failed to send email.")
            }
        }
    }

}
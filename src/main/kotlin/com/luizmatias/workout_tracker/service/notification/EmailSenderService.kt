package com.luizmatias.workout_tracker.service.notification

import com.luizmatias.workout_tracker.config.api.exception.common_exceptions.InternalServerErrorException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class EmailSenderService @Autowired constructor(
    private val mailgunEmailSender: MailgunEmailSender,
    private val sendGridEmailSender: SendGridEmailSender,
) : NotificationSenderService {
    private val logger = LoggerFactory.getLogger(EmailSenderService::class.java)

    override fun send(
        to: String,
        subject: String,
        body: String,
    ) {
        try {
            mailgunEmailSender.send(to, subject, body)
        } catch (e: InternalServerErrorException) {
            logger.warn("Failed to send email using Mailgun. Trying SendGrid. Error: $e")
            try {
                sendGridEmailSender.send(to, subject, body)
            } catch (e: InternalServerErrorException) {
                logger.error("Failed to send email using Sendgrid. No more fallback services. Error: $e")
                throw InternalServerErrorException("Failed to send email.")
            }
        }
    }
}

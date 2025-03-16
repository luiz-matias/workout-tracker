package com.luizmatias.workout_tracker.features.notification.service

import com.luizmatias.workout_tracker.config.exception.common_exceptions.InternalServerErrorException
import com.mailgun.api.v3.MailgunMessagesApi
import com.mailgun.client.MailgunClient
import com.mailgun.model.message.Message
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class MailgunEmailSender(
    @Value("\${mailgun.api-key}")
    private val apiKey: String,
    @Value("\${sendgrid.from.email}")
    private val applicationEmail: String,
    @Value("\${mailgun.domain}")
    private val domain: String,
) {
    private val mailgunMessagesApi = MailgunClient.config(apiKey).createApi(MailgunMessagesApi::class.java)
    private val logger = LoggerFactory.getLogger(MailgunEmailSender::class.java)

    fun send(
        to: String,
        subject: String,
        body: String,
    ) {
        val message =
            Message
                .builder()
                .from(applicationEmail)
                .to(to)
                .subject(subject)
                .text(body)
                .build()

        try {
            val response = mailgunMessagesApi.sendMessage(domain, message)
            if (response.message != "Queued. Thank you.") {
                throw InternalServerErrorException("Failed to send email.")
            }
        } catch (e: Exception) {
            logger.error("Failed to send email using Mailgun. Error: $e")
            throw InternalServerErrorException("Failed to send email.")
        }
    }
}

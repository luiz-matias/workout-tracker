package com.luizmatias.workout_tracker.service.email

import com.luizmatias.workout_tracker.config.exception.common_exceptions.InternalServerErrorException
import com.sendgrid.Method
import com.sendgrid.Request
import com.sendgrid.SendGrid
import com.sendgrid.helpers.mail.Mail
import com.sendgrid.helpers.mail.objects.Content
import com.sendgrid.helpers.mail.objects.Email
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component

@Component
class SendGridEmailSender(
    @Value("\${sendgrid.api-key}")
    private val apiKey: String,
    @Value("\${sendgrid.from.email}")
    private val applicationEmail: String
) {

    fun send(to: String, subject: String, body: String) {
        val mailFrom = Email(applicationEmail)
        val mailTo = Email(to)
        val content = Content("text/plain", body)
        val mail = Mail(mailFrom, subject, mailTo, content)
        val sg = SendGrid(apiKey)
        val request = Request()

        try {
            request.method = Method.POST
            request.endpoint = "mail/send"
            request.body = mail.build()
            val response = sg.api(request)
            if (response.statusCode != HttpStatus.ACCEPTED.value()) {
                throw InternalServerErrorException("Failed to send email.")
            }
        } catch (e: Exception) {
            throw InternalServerErrorException("Failed to send email.")
        }
    }

}
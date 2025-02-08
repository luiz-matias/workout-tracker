package com.luizmatias.workout_tracker.service.email

interface NotificationSenderService {

    fun send(to: String, subject: String, body: String)

}
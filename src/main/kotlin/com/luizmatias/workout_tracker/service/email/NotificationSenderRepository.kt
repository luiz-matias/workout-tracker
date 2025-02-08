package com.luizmatias.workout_tracker.service.email

interface NotificationSenderRepository {

    fun send(to: String, subject: String, body: String)

}
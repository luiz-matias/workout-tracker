package com.luizmatias.workout_tracker.service.notification

interface NotificationSenderService {
    fun send(
        to: String,
        subject: String,
        body: String,
    )
}

package com.luizmatias.workout_tracker.features.notification.service

/**
 * Service for sending notifications. This service is accountable for sending notifications to users.
 */
interface NotificationSenderService {
    /**
     * Send a notification to a user.
     */
    fun send(
        to: String,
        subject: String,
        body: String,
    )
}

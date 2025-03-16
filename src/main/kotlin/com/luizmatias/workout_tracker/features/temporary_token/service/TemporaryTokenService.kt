package com.luizmatias.workout_tracker.features.temporary_token.service

import com.luizmatias.workout_tracker.features.temporary_token.model.TemporaryToken

/**
 * Service for managing temporary tokens. This service is accountable for managing temporary tokens, such as creating,
 * getting, and deleting them.
 *
 * Temporary tokens are used to manage password resets, group invitations, email verifications.
 */
interface TemporaryTokenService {
    /**
     * Create a new temporary token.
     */
    fun createTemporaryToken(token: TemporaryToken): TemporaryToken

    /**
     * Get a temporary token by its token.
     */
    fun getTemporaryTokenByToken(token: String): TemporaryToken?

    /**
     * Delete a temporary token.
     */
    fun deleteTemporaryToken(token: TemporaryToken)
}

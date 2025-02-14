package com.luizmatias.workout_tracker.service.temporary_token

import com.luizmatias.workout_tracker.model.temporary_token.TemporaryToken

interface TemporaryTokenService {
    fun createTemporaryToken(token: TemporaryToken): TemporaryToken

    fun getTemporaryTokenByToken(token: String): TemporaryToken?

    fun deleteTemporaryToken(token: TemporaryToken): Boolean
}

package com.luizmatias.workout_tracker.features.temporary_token.service

import com.luizmatias.workout_tracker.features.temporary_token.model.TemporaryToken
import com.luizmatias.workout_tracker.features.temporary_token.repository.TemporaryTokenRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TemporaryTokenServiceImpl @Autowired constructor(
    private val temporaryTokenRepository: TemporaryTokenRepository,
) : TemporaryTokenService {
    override fun createTemporaryToken(token: TemporaryToken): TemporaryToken = temporaryTokenRepository.save(token)

    override fun getTemporaryTokenByToken(token: String): TemporaryToken? = temporaryTokenRepository.findByToken(token)

    override fun deleteTemporaryToken(token: TemporaryToken) {
        token.id?.let {
            if (temporaryTokenRepository.existsById(it)) {
                temporaryTokenRepository.deleteById(it)
            }
        }
    }
}

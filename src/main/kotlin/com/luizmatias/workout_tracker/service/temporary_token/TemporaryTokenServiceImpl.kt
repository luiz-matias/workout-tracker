package com.luizmatias.workout_tracker.service.temporary_token

import com.luizmatias.workout_tracker.model.temporary_token.TemporaryToken
import com.luizmatias.workout_tracker.repository.TemporaryTokenRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TemporaryTokenServiceImpl @Autowired constructor(
    private val temporaryTokenRepository: TemporaryTokenRepository,
) : TemporaryTokenService {
    override fun createTemporaryToken(token: TemporaryToken): TemporaryToken = temporaryTokenRepository.save(token)

    override fun getTemporaryTokenByToken(token: String): TemporaryToken? = temporaryTokenRepository.findByToken(token)

    override fun deleteTemporaryToken(token: TemporaryToken): Boolean {
        token.id?.let {
            if (temporaryTokenRepository.existsById(it)) {
                temporaryTokenRepository.deleteById(it)
                return true
            }
        }
        return false
    }
}

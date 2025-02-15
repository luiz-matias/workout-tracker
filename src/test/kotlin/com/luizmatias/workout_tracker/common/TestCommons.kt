package com.luizmatias.workout_tracker.common

import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath

/**
 * Asserts that the response body contains the error fields.
 * @param extras Additional fields to be checked under the `extras` json child object
 * (useful when validation errors occurs).
 */
fun ResultActions.andExpectErrorBody(vararg extras: String = arrayOf()) {
    this.andExpect(jsonPath("timestamp").exists())
    this.andExpect(jsonPath("status").exists())
    this.andExpect(jsonPath("error").exists())
    this.andExpect(jsonPath("path").exists())
    extras.forEach { this.andExpect(jsonPath("extras.$it").exists()) }
}

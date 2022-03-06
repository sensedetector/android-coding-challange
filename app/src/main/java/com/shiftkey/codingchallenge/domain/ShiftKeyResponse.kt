package com.shiftkey.codingchallenge.domain

import com.google.gson.annotations.JsonAdapter
import com.shiftkey.codingchallenge.data.ShiftKeyResponseDeserializer

data class ShiftKeyResponse(
    @JsonAdapter(ShiftKeyResponseDeserializer::class)
    val shifts: List<Shift>
)

package com.shiftkey.codingchallenge.data

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.shiftkey.codingchallenge.domain.Shift
import com.shiftkey.codingchallenge.domain.ShiftKeyResponse
import java.lang.reflect.Type

class ShiftKeyResponseDeserializer: JsonDeserializer<ShiftKeyResponse> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): ShiftKeyResponse {
        context ?: return ShiftKeyResponse(emptyList())

        val shiftsArray = json?.asJsonObject
            ?.getAsJsonArray("data")
            ?.map { it.asJsonObject.getAsJsonArray("shifts") }
            ?.flatten()

        val shifts: List<Shift> = shiftsArray?.map { element ->
            context.deserialize(element, Shift::class.java)
        } ?: emptyList()

        return ShiftKeyResponse(shifts)
    }
}

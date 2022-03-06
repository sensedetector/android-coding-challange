package com.shiftkey.codingchallenge.domain

import com.google.gson.annotations.SerializedName

data class Shift (
    @SerializedName("shift_id") val shiftId: Int,
    @SerializedName("normalized_start_date_time") val startTime: String,
    @SerializedName("normalized_end_date_time") val endTime: String,
    @SerializedName("shift_kind") val shiftKind: String,
    @SerializedName("within_distance") val distance: Int,
    @SerializedName("covid") val covid: Boolean,
    @SerializedName("premium_rate") val premium: Boolean,
    @SerializedName("facility_type") val facilityType: FacilityType,
    @SerializedName("skill") val skill: Skill,
    @SerializedName("localized_specialty") val localizedSpecialty: LocalizedSpecialty,
)

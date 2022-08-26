package com.rawtooth.swachta.schedule

data class ScheduleModel(
    val date: String,
    val location: String,
    val time: String,
    val userId: String,
    val wasteDetails: String
)
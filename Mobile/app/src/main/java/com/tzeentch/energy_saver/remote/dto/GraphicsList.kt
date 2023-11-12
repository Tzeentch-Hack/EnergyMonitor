package com.tzeentch.energy_saver.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GraphicsList(
    @SerialName("watt_consumption") val wattConsumption:String,
    @SerialName("sum_consumption") val sumConsumption:String,
    @SerialName("date_time") val dateTime:String
)

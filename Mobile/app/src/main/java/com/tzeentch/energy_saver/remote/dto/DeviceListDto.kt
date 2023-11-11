package com.tzeentch.energy_saver.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeviceDto(
    @SerialName("consumer_id") val deviceId: String,
    @SerialName("enabled") val isEnabled: String,
    @SerialName("device_name") val deviceName: String,
    @SerialName("started_time") val startTime: String,
    @SerialName("working_time") val workingTime: String,
    @SerialName("watt_consumption") val wattConsumption:String,
    @SerialName("sum_consumption") val sumConsumption:String?,
    @SerialName("consumption_summary") val consumptionSummary:String
)

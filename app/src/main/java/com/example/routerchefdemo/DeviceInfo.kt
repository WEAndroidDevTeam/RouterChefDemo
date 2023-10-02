package com.example.routerchefdemo

data class DeviceInfo(
    val deviceName: String,
    val serialNumber: String,
    val softwareVersion: String,
    val hardwareVersion: String,
    val uptime: Long
)

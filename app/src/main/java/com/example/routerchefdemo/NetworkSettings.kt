package com.example.routerchefdemo

data class NetworkSettings(
    val transmitPower: Int,
    val wmmStatus: Boolean,
    val bandwidth: String,
    val mcs: Int
)


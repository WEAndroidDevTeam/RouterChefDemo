package com.example.routerchefdemo

data class DslDetails(
    val status: String?,
    val modulation: String?,
    val upCurrRate: Int?,
    val downCurrRate: Int?,
    val downstreamMaxBitRate: Int?,
    val upstreamMaxBitRate: Int?,
    val impulsoNoiseProUs: Int?,
    val impulsoNoiseProDs: Int?,
    val downAttenuation: Int?,
    val upAttenuation: Int?,
    val upPower: Int?,
    val downPower: Int?,
    val dslUpTime: String?
)

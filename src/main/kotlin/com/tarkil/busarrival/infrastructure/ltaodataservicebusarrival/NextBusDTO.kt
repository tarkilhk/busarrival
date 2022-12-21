package com.tarkil.busarrival.infrastructure.ltaodataservicebusarrival

data class NextBusDTO(
    val DestinationCode: String,
    val EstimatedArrival: String,
    val Feature: String,
    val Latitude: String,
    val Load: String,
    val Longitude: String,
    val OriginCode: String,
    val Type: String,
    val VisitNumber: String
)
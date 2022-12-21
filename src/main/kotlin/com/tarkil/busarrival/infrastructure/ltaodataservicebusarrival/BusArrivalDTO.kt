package com.tarkil.busarrival.infrastructure.ltaodataservicebusarrival

data class BusArrivalDTO(
    val BusStopCode: String,
    val Services: List<ServiceDTO>
)
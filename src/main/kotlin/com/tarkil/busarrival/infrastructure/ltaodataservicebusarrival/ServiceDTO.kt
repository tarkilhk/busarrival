package com.tarkil.busarrival.infrastructure.ltaodataservicebusarrival

data class ServiceDTO(
    val NextBus: NextBusDTO,
    val NextBus2: NextBusDTO,
    val NextBus3: NextBusDTO,
    val Operator: String,
    val ServiceNo: String
)
package com.tarkil.busarrival.domain.busarrival

import com.tarkil.busarrival.infrastructure.ltaodataservicebusarrival.BusArrivalDTO
import com.tarkil.busarrival.infrastructure.ltaodataservicebusarrival.NextBusDTO
import com.tarkil.busarrival.infrastructure.ltaodataservicebusarrival.ServiceDTO
import java.time.ZonedDateTime

class BusArrival() {
    var BusStopCode: String = "0"
    var Services: MutableList<Service> = mutableListOf<Service>()
    var message: String = ""
    var isError: Boolean = false
    var lastRefreshTime: ZonedDateTime = ZonedDateTime.now()

    constructor(
        busArrivalDto: BusArrivalDTO,
        message: String,
        isError: Boolean,
        lastRefreshTime: ZonedDateTime
    ) : this() {
        this.BusStopCode = busArrivalDto.BusStopCode
        this.Services.addAll(busArrivalDto.Services.map { Service(it) })
        this.message = message
        this.lastRefreshTime = lastRefreshTime
    }

    constructor(message: String, isError: Boolean) : this() {
        this.message = message
        this.isError = isError
        this.lastRefreshTime = ZonedDateTime.now()
    }
}

data class NextBus(
    val estimatedArrival: String = "",
    val load: String = "",
    val type: String = ""
) {
    constructor(nextBusDTO: NextBusDTO) :
            this(
                estimatedArrival = nextBusDTO.EstimatedArrival,
                load = nextBusDTO.Load,
                type = nextBusDTO.Type
            )
}

data class Service(
    var nextBus: NextBus = NextBus(),
    var nextBus2: NextBus = NextBus(),
    var nextBus3: NextBus = NextBus(),
    var operator: String = "",
    var serviceNo: String = ""
) {
    constructor(serviceDTO: ServiceDTO) :
            this(
                nextBus = NextBus(serviceDTO.NextBus),
                nextBus2 = NextBus(serviceDTO.NextBus2),
                nextBus3 = NextBus(serviceDTO.NextBus3),
                operator = serviceDTO.Operator,
                serviceNo = serviceDTO.ServiceNo
            )
}
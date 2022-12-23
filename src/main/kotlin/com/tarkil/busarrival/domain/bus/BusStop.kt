package com.tarkil.busarrival.domain.bus

import com.tarkil.busarrival.infrastructure.datapersistence.bus.BusStopDAO
import java.util.*

data class BusStop(
    var busStopId: Long = -1,
    var busStopName: String = "",
    var busStopCode: String = "",
    var serviceNo: String = ""
) {

    constructor(busStopDAO: BusStopDAO) : this(
        busStopId = busStopDAO.busStopId,
        busStopName = busStopDAO.name,
        busStopCode = busStopDAO.busStopCode,
        serviceNo = busStopDAO.serviceNo
    )

    constructor(busStopCode: String, busStopName: String, serviceNo: String) : this() {
        this.busStopId = -1
        this.busStopName = busStopName
        this.busStopCode = busStopCode
        this.serviceNo = serviceNo
    }

    override fun toString(): String = "[$busStopId] $busStopName - busStopCode = $busStopCode - serviceNo = $serviceNo"

    override fun hashCode(): Int {
        return (Objects.hash(busStopName, busStopCode))
    }

    override fun equals(other: Any?): Boolean {
        if (other == null || javaClass != other.javaClass) return false
        val that = other as BusStop
        return this.busStopName == that.busStopName &&
                this.busStopCode == that.busStopCode
    }

    fun toDAO(): BusStopDAO {
        return BusStopDAO(
            busStopId = this.busStopId,
            name = this.busStopName,
            busStopCode = this.busStopCode,
            serviceNo = this.serviceNo,
            userDAOs = mutableListOf()
        )
    }
}
package com.tarkil.busarrival.domain.bus

import com.tarkil.busarrival.infrastructure.datapersistence.bus.BusStopDAO
import com.tarkil.busarrival.infrastructure.datapersistence.bus.FavouriteBusStopDAO
import java.util.*

data class BusStop(
    var busStopId: Long = -1,
    var busStopName: String = "",
    var busStopCode: String = ""
) {

    constructor(busStopDAO: BusStopDAO) : this(
        busStopId = busStopDAO.busStopId,
        busStopName = busStopDAO.name,
        busStopCode = busStopDAO.busStopCode
    )

    constructor(favouriteBusStopDAO: FavouriteBusStopDAO) : this(
        busStopId = favouriteBusStopDAO.busStopDAO.busStopId,
        busStopName = favouriteBusStopDAO.busStopDAO.name,
        busStopCode = favouriteBusStopDAO.busStopDAO.busStopCode
    )

    constructor(busStopCode: String, busStopName: String) : this() {
        this.busStopId = -1
        this.busStopName = busStopName
        this.busStopCode = busStopCode
    }

    override fun toString(): String = "[$busStopId] $busStopName - busStopCode = $busStopCode"

    override fun hashCode(): Int {
        return (Objects.hash(this.busStopName, this.busStopCode))
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
            busStopCode = this.busStopCode
        )
    }
}
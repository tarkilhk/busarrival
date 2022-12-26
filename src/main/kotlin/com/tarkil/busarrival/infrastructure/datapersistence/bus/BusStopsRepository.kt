package com.tarkil.busarrival.infrastructure.datapersistence.bus

import org.springframework.data.repository.CrudRepository


interface BusStopsRepository : CrudRepository<BusStopDAO, Long> {
    abstract fun existsByName(name: String): Boolean

    abstract fun existsByBusStopCode(busStopCode: String): Boolean

    fun getByName(name: String): BusStopDAO

    fun getByBusStopCode(busStopCode: String): BusStopDAO
}
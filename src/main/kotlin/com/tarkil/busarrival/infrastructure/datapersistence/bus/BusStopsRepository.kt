package com.tarkil.busarrival.infrastructure.datapersistence.bus

import org.springframework.data.repository.CrudRepository


interface BusStopsRepository : CrudRepository<BusStopDAO, Long> {
    abstract fun existsByNameAndServiceNo(name: String, serviceNo: String): Boolean

    abstract fun existsByBusStopCode(busStopCode: String): Boolean

    fun getByNameAndServiceNo(name: String, serviceNo: String): BusStopDAO

    fun getByBusStopCode(busStopCode: String): BusStopDAO
}
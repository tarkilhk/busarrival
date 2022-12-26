package com.tarkil.busarrival.domain.bus

import com.tarkil.busarrival.infrastructure.datapersistence.bus.BusStopDAO
import com.tarkil.busarrival.infrastructure.datapersistence.bus.BusStopsRepository
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class BusService(private val busStopsRepository: BusStopsRepository) {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    fun upsertNewBusStop(newBusStop: BusStop): Long {
        if (busStopsRepository.existsByName(
                name = newBusStop.busStopName
            )
        ) {
            logger.info("Found busStop with name ${newBusStop.busStopName} to update")

            val toBeUpdatedBusStopDAO: BusStopDAO =
                busStopsRepository.getByName(newBusStop.busStopName)

            toBeUpdatedBusStopDAO.updateMembers(newBusStop)
            busStopsRepository.save(toBeUpdatedBusStopDAO)
            logger.info("Successfully updated ${newBusStop.busStopName} [stopId ${newBusStop.busStopId}] in DB")
            return toBeUpdatedBusStopDAO.busStopId
        } else {
            logger.info("Creating a new bus stop with name ${newBusStop.busStopName}")

            val insertedBusStopId: Long = busStopsRepository.save(newBusStop.toDAO()).busStopId
            logger.info("Successfully added ${newBusStop.busStopName} in DB")
            return insertedBusStopId
        }
    }

    fun getBusStopByBusStopId(stopId: Long): BusStop {
        return if (busStopsRepository.existsById(stopId)) {
            BusStop(this.busStopsRepository.findByIdOrNull(stopId)!!)
        } else {
            BusStop(busStopId = -1, busStopName = "Unknown Bus Stop", busStopCode = "")
        }
    }

    fun busStopExistsById(stopId: Long): Boolean {
        return this.busStopsRepository.existsById(stopId)
    }

    fun getAllBusStops(): List<BusStop> {
        return this.busStopsRepository.findAll().map { BusStop(it) }
    }
}
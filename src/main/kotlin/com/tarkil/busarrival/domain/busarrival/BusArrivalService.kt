package com.tarkil.busarrival.domain.busarrival

import com.tarkil.busarrival.infrastructure.ltaodataservicebusarrival.BusArrivalDTO
import com.tarkil.busarrival.infrastructure.ltaodataservicebusarrival.LTAODataServiceBusArrivalV2
import feign.FeignException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.ZonedDateTime

@Service
class BusArrivalService(val ltaoDataServiceBusArrivalV2: LTAODataServiceBusArrivalV2) {
    private val logger = LoggerFactory.getLogger(this.javaClass)
    private val accountKey = "CrWrLQT/Q5uCyecZ5hA7eg=="

    fun getBusArrivalForBusStopCode(busStopCode: String): BusArrival {
        logger.info("Before feign call to LTAO DataService BusArrivalV2 with parameter BusStopCode='$busStopCode'")
        try {
            val busArrivalDTO: BusArrivalDTO =
                ltaoDataServiceBusArrivalV2.getBusArrival(accountKey = accountKey, busStopCode = busStopCode)
            logger.info("Successfully retrieved data from LTAO Data Service")
            return BusArrival(busArrivalDTO, "OK", false, ZonedDateTime.now())
        } catch (e: FeignException) {
            val errorMessage =
                "FeignException while trying to retrieve data from LTAO Data Service : [${e.status()}] : ${e.message}"
            logger.error(errorMessage)
            return BusArrival(message = errorMessage, isError = true)
        } catch (e: Exception) {
            logger.error("Exception while trying to retrieve data from LTAO Data Service : ${e.message}")
            return BusArrival(
                message = "Exception while trying to retrieve data from LTAO Data Service : ${e.message}",
                isError = true
            )
        }
    }

    fun getBusArrivalForBusStopCodeAndServiceNos(serviceNos: MutableList<String>, busStopCode: String): BusArrival {
        val busArrival = this.getBusArrivalForBusStopCode(busStopCode)
        val filteredServices = busArrival.Services.filter { it.serviceNo in serviceNos }.toMutableList()
        if (filteredServices.size == 0) {
            return BusArrival(message = "No bus", isError = false)
        } else {
            busArrival.Services = filteredServices
            return busArrival
        }
    }
}
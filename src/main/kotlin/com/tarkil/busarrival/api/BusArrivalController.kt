package com.tarkil.busarrival.api

import com.tarkil.busarrival.domain.busarrival.BusArrival
import com.tarkil.busarrival.domain.busarrival.BusArrivalService
import feign.FeignException.NotFound
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/bus-arrival")
class BusArrivalController(val busArrivalService: BusArrivalService) {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    @GetMapping("/bus-stop-code")
    fun getBusArrivalForBusStopCode(
        @RequestParam(value = "busStopCode") busStopCode: String
    ): ResponseEntity<BusArrival> {
        return try {
            val busArrival = busArrivalService.getBusArrivalForBusStopCode(busStopCode)
            ResponseEntity.ok(busArrival)
        } catch (e: NotFound) {
            ResponseEntity(BusArrival(message = "" + e.message, isError = true), HttpStatus.NOT_FOUND)
        } catch (e: Exception) {
            ResponseEntity(
                BusArrival(message = "" + e.message, isError = true), HttpStatus.INTERNAL_SERVER_ERROR
            )
        }
    }

    @GetMapping("/bus-stop-code-and-service-nos")
    fun getBusArrivalForBusStopCodeAndServiceNos(
        @RequestParam(value = "busStopCode") busStopCode: String,
        @RequestParam(value = "serviceNos") serviceNos: MutableList<String>
    ): ResponseEntity<BusArrival> {
        return try {
            val busArrival = busArrivalService.getBusArrivalForBusStopCodeAndServiceNos(
                busStopCode = busStopCode,
                serviceNos = serviceNos
            )
            ResponseEntity.ok(busArrival)
        } catch (e: NotFound) {
            ResponseEntity(BusArrival(message = "" + e.message, isError = true), HttpStatus.NOT_FOUND)
        } catch (e: Exception) {
            ResponseEntity(
                BusArrival(message = "" + e.message, isError = true), HttpStatus.INTERNAL_SERVER_ERROR
            )
        }
    }
}

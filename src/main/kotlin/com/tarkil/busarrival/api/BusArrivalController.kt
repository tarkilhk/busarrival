package com.tarkil.busarrival.api

import com.tarkil.busarrival.domain.busarrival.BusArrival
import com.tarkil.busarrival.domain.busarrival.BusArrivalService
import feign.FeignException.NotFound
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController()
@RequestMapping("/bus-arrival")
class BusArrivalController(val busArrivalService: BusArrivalService) {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    @GetMapping("/bus-stop-code")
    fun getBusArrivalForBusStopCode(
        @RequestParam(value = "busStopCode") busStopCode: String
    ): ResponseEntity<BusArrival> {
        try {
            val busArrival = busArrivalService.getBusArrivalForBusStopCode(busStopCode)
            return ResponseEntity.ok(busArrival)
        } catch (e: NotFound) {
            return ResponseEntity(BusArrival(message = "" + e.message, isError = true), HttpStatus.NOT_FOUND)
        } catch (e: Exception) {
            return ResponseEntity(
                BusArrival(message = "" + e.message, isError = true),
                HttpStatus.INTERNAL_SERVER_ERROR
            )
        }
    }

    @GetMapping("/bus-route-and-bus-stop-code")
    fun getBusArrivalForBusRouteAndBusStopCode(
        @RequestParam(value = "serviceNo") serviceNo: String,
        @RequestParam(value = "busStopCode") busStopCode: String
    ): ResponseEntity<BusArrival> {
        try {
            val busArrival =
                busArrivalService.getBusArrivalForServiceNoAndBusStopCode(
                    serviceNo = serviceNo,
                    busStopCode = busStopCode
                )
            return ResponseEntity.ok(busArrival)
        } catch (e: NotFound) {
            return ResponseEntity(BusArrival(message = "" + e.message, isError = true), HttpStatus.NOT_FOUND)
        } catch (e: Exception) {
            return ResponseEntity(
                BusArrival(message = "" + e.message, isError = true),
                HttpStatus.INTERNAL_SERVER_ERROR
            )
        }
    }
}

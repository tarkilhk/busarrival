package com.tarkil.busarrival.api.admin

import com.tarkil.busarrival.domain.bus.BusService
import com.tarkil.busarrival.domain.bus.BusStop
import feign.FeignException.NotFound
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/admin/bus")
class BusController(val busService: BusService) {

    @PostMapping("/bus-stops")
    fun newBusStop(
        @RequestParam(value = "busStopCode") busStopCode: String,
        @RequestParam(value = "name") name: String,
        @RequestParam(value = "serviceNo") serviceNo: String
    ): ResponseEntity<String> {
        var response: ResponseEntity<String>
        try {
            val newBusStopId = busService.upsertNewBusStop(
                BusStop(
                    busStopCode = busStopCode,
                    busStopName = name
                )
            )
            response = ResponseEntity("Done : id $newBusStopId", HttpStatus.OK)
        } catch (e: NotFound) {
            response = ResponseEntity(e.message, HttpStatus.NOT_FOUND)
        } catch (e: Exception) {
            response = ResponseEntity(e.message, HttpStatus.INTERNAL_SERVER_ERROR)
        }

        return (response)
    }

    @GetMapping("/bus-stops")
    fun getBusStops(): List<BusStop> {
        return busService.getAllBusStops()
    }
}
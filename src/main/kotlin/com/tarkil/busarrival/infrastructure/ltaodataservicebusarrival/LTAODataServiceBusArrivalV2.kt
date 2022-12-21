package com.tarkil.busarrival.infrastructure.ltaodataservicebusarrival

import feign.Headers
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(value = "ltao-dataservice-busarrivalv2", url = "http://datamall2.mytransport.sg/ltaodataservice/")
interface LTAODataServiceBusArrivalV2 {

    @Headers("Accept:application/json")
//    @Headers("AccountKey:CrWrLQT/Q5uCyecZ5hA7eg==")
    @GetMapping("BusArrivalv2")
    fun getBusArrival(
        @RequestHeader("AccountKey") accountKey: String,
        @RequestParam("BusStopCode") busStopCode: String
    ): BusArrivalDTO
//    fun getBusArrival(@Param("accountKey") accountKey:String, @RequestParam("busStopCode") busStopCode:String) : BusArrivalDTO
//    fun getETA(@PathVariable("stopId") stopId:String) : feign.Response;
}
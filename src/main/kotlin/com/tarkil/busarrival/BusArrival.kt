package com.tarkil.busarrival

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@EnableFeignClients
@SpringBootApplication
class BusArrivalApplication

fun main(args: Array<String>) {
	runApplication<BusArrivalApplication>(*args)
}

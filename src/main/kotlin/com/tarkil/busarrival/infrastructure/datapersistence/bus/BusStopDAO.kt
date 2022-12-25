package com.tarkil.busarrival.infrastructure.datapersistence.bus

import com.tarkil.busarrival.domain.bus.BusStop
import com.tarkil.busarrival.infrastructure.datapersistence.users.UserDAO
import javax.persistence.*

@Entity
@Table(name = "BUS_STOPS")
class BusStopDAO(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val busStopId: Long = -1,

    @Column()
    val name: String,

    @Column
    var busStopCode: String,

    @Column
    var serviceNo: String,

    @ManyToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "favouriteBusStopsDAOS")
    val userDAOs: MutableList<UserDAO> = mutableListOf<UserDAO>()
) {
    fun updateMembers(newBusStop: BusStop) {
        this.busStopCode = newBusStop.busStopCode
        this.serviceNo = newBusStop.serviceNo
    }

    override fun toString(): String {
        return "[$busStopId] $name - busStopCode = $busStopCode - serviceNo = $serviceNo"
    }
}

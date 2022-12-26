package com.tarkil.busarrival.infrastructure.datapersistence.bus

import com.tarkil.busarrival.domain.bus.BusStop
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


//    @ManyToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "favouriteBusStopsDAOS")
//    val userDAOs: MutableList<UserDAO> = mutableListOf<UserDAO>()

//    @OneToMany(mappedBy = "busStopDAO")
//    val favouriteBusStopsDAOS: MutableList<FavouriteBusStopDAO> = mutableListOf()
) {
    fun updateMembers(newBusStop: BusStop) {
        this.busStopCode = newBusStop.busStopCode
    }

    override fun toString(): String {
        return "[$busStopId] $name - busStopCode = $busStopCode"
    }
}

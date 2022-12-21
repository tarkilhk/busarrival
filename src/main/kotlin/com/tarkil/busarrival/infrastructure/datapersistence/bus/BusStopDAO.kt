package com.tarkil.busarrival.infrastructure.datapersistence.bus

import com.tarkil.busarrival.infrastructure.datapersistence.users.UserDAO
import javax.persistence.*

@Entity
@Table(name = "BUS_STOPS")
class BusStopDAO(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val busStopId: Long = -1,

    @Column(unique = true)
    val name: String,

    @Column
    var busStopCode: String,

    @ManyToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "favouriteBusStopsDAOS")
    val userDAOs: MutableList<UserDAO> = mutableListOf<UserDAO>()
) {
    fun changeBusStopCode(busStopCode: String) {
        this.busStopCode = busStopCode
    }
//    constructor(name: String) : this(
//        name = name,
//        busStopId = -1,
//        busStopCode = "",
//        userDaos = mutableListOf<UserDAO>()
//    )
}

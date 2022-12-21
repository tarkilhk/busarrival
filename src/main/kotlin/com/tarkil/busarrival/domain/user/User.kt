package com.tarkil.busarrival.domain.user

import com.tarkil.busarrival.domain.bus.BusStop
import com.tarkil.busarrival.infrastructure.datapersistence.users.UserDAO

data class User(
    var userId: Long = -1,
    var userName: String = "",
    var favouriteBusStops: MutableList<BusStop> = mutableListOf<BusStop>()
) {
    constructor(userDAO: UserDAO) : this(
        userId = userDAO.getUserId(), userName = userDAO.name
    ) {
        for (favouriteBusStopDao in userDAO.favouriteBusStopsDAOS) {
            this.favouriteBusStops.add(BusStop(busStopDAO = favouriteBusStopDao))
        }
    }

    fun toDAO(): UserDAO {
        return UserDAO(userId = this.userId, name = this.userName, favouriteBusStopsDAOS = mutableListOf())
    }

    fun updateFavouriteBusStops(busStops: List<BusStop>) {
        this.favouriteBusStops.clear()
        this.favouriteBusStops.addAll(busStops)
    }
}
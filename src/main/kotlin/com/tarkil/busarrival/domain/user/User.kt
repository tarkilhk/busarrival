package com.tarkil.busarrival.domain.user

import com.tarkil.busarrival.domain.bus.BusStop
import com.tarkil.busarrival.domain.bus.FavouriteBusStop
import com.tarkil.busarrival.infrastructure.datapersistence.users.UserDAO

data class User(
    var userId: Long = -1,
    var userName: String = "",
    var favouriteBusStops: MutableList<FavouriteBusStop> = mutableListOf<FavouriteBusStop>()
) {
    constructor(userDAO: UserDAO) : this(
        userId = userDAO.getUserId(), userName = userDAO.name
    ) {
        for (favouriteBusStopDao in userDAO.favouriteBusStopsDAOS) {
            this.favouriteBusStops.add(
                FavouriteBusStop(
                    busStop = BusStop(favouriteBusStopDao),
                    favouriteBusStopDao.serviceNo
                )
            )
        }
    }

    fun toDAO(): UserDAO {
        return UserDAO(userId = this.userId, name = this.userName, favouriteBusStopsDAOS = mutableListOf())
    }

    fun replaceFavouriteBusStops(favouriteBusStops: List<FavouriteBusStop>) {
        this.favouriteBusStops.clear()
        this.favouriteBusStops.addAll(favouriteBusStops)
    }

    fun getFavouriteBusStopsAsAPIResponse(): MutableList<FavouriteBusStopResponse> {
        val stopsWithListOfServiceNos: HashMap<BusStop, MutableList<String>> = hashMapOf()
        for (favouriteBusStop in this.favouriteBusStops) {
            if (stopsWithListOfServiceNos.containsKey(favouriteBusStop.busStop)) {
                stopsWithListOfServiceNos[favouriteBusStop.busStop]!!.add(favouriteBusStop.serviceNo)
            } else {
                stopsWithListOfServiceNos[favouriteBusStop.busStop] = mutableListOf()
                stopsWithListOfServiceNos[favouriteBusStop.busStop]!!.add(favouriteBusStop.serviceNo)
            }
        }

        val response = mutableListOf<FavouriteBusStopResponse>()
        for ((favouriteBusStop: BusStop, serviceNos: MutableList<String>) in stopsWithListOfServiceNos) {
            response.add(FavouriteBusStopResponse(busStop = favouriteBusStop, serviceNos = serviceNos))
        }
        return response
    }
}

data class FavouriteBusStopResponse(
    var busStop: BusStop,
    var serviceNos: MutableList<String>
)

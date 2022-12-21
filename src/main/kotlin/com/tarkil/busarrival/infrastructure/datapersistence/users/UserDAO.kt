package com.tarkil.busarrival.infrastructure.datapersistence.users

import com.tarkil.busarrival.infrastructure.datapersistence.bus.BusStopDAO
import javax.persistence.*


@Entity
@Table(name = "USERS")
class UserDAO(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private val userId: Long = -1,

    @Column(unique = true)
    val name: String,

    @ManyToMany
    @JoinTable(
        name = "USERS_BUS_STOPS",
        joinColumns = arrayOf(JoinColumn(name = "userId")),
        inverseJoinColumns = arrayOf(JoinColumn(name = "busStopId"))
    )
    val favouriteBusStopsDAOS: MutableList<BusStopDAO> = mutableListOf()
) {
    constructor() : this(-1, "")

    override fun toString(): String {
        return String.format(
            "User id=$userId : $name"
        )
    }

    fun getUserId(): Long {
        return this.userId
    }

//    fun getAllFavouriteStopsForGroup(name: String) : MutableList<FavouriteGroup>
//    {
//        return this.favouriteGroups.filter{it.shortName==name}.toMutableList()
//    }
//
//    fun getAllChosenBusStops() : MutableList<BusStopConfig>
//    {
//        val chosenBusStops = mutableListOf<BusStopConfig>()
//        for(desiredBustStop in favouriteGroups) {
//            chosenBusStops.add(BusStopConfig(desiredBustStop.busNumber, desiredBustStop.officialHKBusStopId))
//        }
//        return chosenBusStops
//    }
//
//    fun getAllChosenBusStopsForGroup(name: String) : MutableList<BusStopConfig>
//    {
//        val chosenBusStops = mutableListOf<BusStopConfig>()
//        var nameToQuery: String = name
//        if (nameToQuery == "default") {
//            //This happens when user logins, and doesn't specify a default config
//            //TODO : load default config from DB instead, after isDefault has been implemented
//            nameToQuery = "CastleDown   \uD83C\uDFF0 \uD83D\uDC47"
//        }
//        val desiredBusStops = this.favouriteGroups.filter { it.shortName == nameToQuery }.toMutableList()
//        for (desiredBusStop in desiredBusStops) {
//            chosenBusStops.add(BusStopConfig(desiredBusStop.busNumber, desiredBusStop.officialHKBusStopId))
//        }
//        return chosenBusStops
//    }
//
//    @Transactional
//    fun attachDesiredBusStop(favouriteGroup: FavouriteGroup) {
//        this.favouriteGroups.add(favouriteGroup)
//    }
}
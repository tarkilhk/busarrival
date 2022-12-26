package com.tarkil.busarrival.infrastructure.datapersistence.bus

import com.tarkil.busarrival.infrastructure.datapersistence.users.UserDAO
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "favourite_bus_stops")
class FavouriteBusStopDAO(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = -1,

    @ManyToOne
    @JoinColumn(name = "userId")
    var userDAO: UserDAO,

    @ManyToOne
    @JoinColumn(name = "busStopId")
    var busStopDAO: BusStopDAO,

    var serviceNo: String = ""
) {
    override fun hashCode(): Int {
        return Objects.hash(this.userDAO, this.busStopDAO, this.serviceNo)
    }

    override fun equals(other: Any?): Boolean {
        if (other == null || javaClass != other.javaClass) return false
        val that = other as FavouriteBusStopDAO
        return this.userDAO == that.userDAO &&
                this.busStopDAO == that.busStopDAO &&
                this.serviceNo == that.serviceNo
    }
}
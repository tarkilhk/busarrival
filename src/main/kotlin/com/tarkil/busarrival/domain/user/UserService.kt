package com.tarkil.busarrival.domain.user

import com.tarkil.busarrival.domain.bus.BusStop
import com.tarkil.busarrival.infrastructure.datapersistence.bus.BusStopsRepository
import com.tarkil.busarrival.infrastructure.datapersistence.users.UserDAO
import com.tarkil.busarrival.infrastructure.datapersistence.users.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserService(val userRepository: UserRepository, val busStopsRepository: BusStopsRepository) {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    fun setFavouriteStopsFor(userId: Long, stopIds: MutableList<Long>) {
        if (userRepository.existsById(userId)) {
            val userDAO: UserDAO = this.getUserDAOById(userId)!!

            userDAO.favouriteBusStopsDAOS.clear()
            for (stopId: Long in stopIds) {
                if (busStopsRepository.existsById(stopId)) {
                    userDAO.favouriteBusStopsDAOS.add(busStopsRepository.findByIdOrNull(stopId)!!)
                } else {
                    // invalid busStopGroupId
                }
            }
            userRepository.save(userDAO)
        } else {
            // invalid user name
        }
    }

    fun getUserByName(name: String): User? {
        if (userRepository.existsByName(name)) {
            return (User(userRepository.getByName(name)))
        }
        return null
    }

    fun getFavouriteStops(userId: Long): MutableList<BusStop> {
        val myUser: User? = this.getUserById(userId)
        if (myUser == null) {
            return mutableListOf(BusStop(busStopId = -1, busStopName = "Unknown user", busStopCode = ""))
        } else {
//            val busStopGroupList : MutableList<BusStopGroup> = mutableListOf()
//            for (busStopGroupDao in myUser.favouriteBusStopGroupsDaos) {
//                busStopGroupList.add(BusStopGroup(busStopGroupsDao = busStopGroupDao))
//            }
//            return(busStopGroupList)
            return myUser.favouriteBusStops
        }
    }

    private fun getUserById(userId: Long): User? {
        if (userRepository.existsById(userId)) {
            return (User(userDAO = userRepository.findById(userId).get()))
        }
        return null
    }

    private fun getUserDAOById(userId: Long): UserDAO? {
        if (userRepository.existsById(userId)) {
            return (userRepository.findById(userId).get())
        }
        return null
    }

    fun getAll(): MutableList<User> {
        val userList: MutableList<User> = mutableListOf<User>()
        for (userDao: UserDAO in userRepository.findAll()) {
            userList.add(User(userDao))
        }
        return userList
    }

    fun insertNewUser(newUser: User): Long {
        if (userRepository.existsByName(newUser.userName)) {
            val userDAO: UserDAO = userRepository.getByName(newUser.userName)
            logger.error("Found already existing user with name ${newUser.userName} [stopId ${userDAO.getUserId()}] in DB - Won't create")
            return userDAO.getUserId()
        } else {
            logger.info("Creating a new user with name ${newUser.userName}")

            val insertedUserId: Long = userRepository.save(newUser.toDAO()).getUserId()
            logger.info("Successfully added ${newUser.userName} in DB [id = ${insertedUserId}]")
            return insertedUserId
        }
    }
}
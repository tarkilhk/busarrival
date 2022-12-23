package com.tarkil.busarrival.domain.user

import com.tarkil.busarrival.domain.bus.BusStop
import com.tarkil.busarrival.infrastructure.datapersistence.bus.BusStopDAO
import com.tarkil.busarrival.infrastructure.datapersistence.bus.BusStopsRepository
import com.tarkil.busarrival.infrastructure.datapersistence.users.UserDAO
import com.tarkil.busarrival.infrastructure.datapersistence.users.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.webjars.NotFoundException

@Service
class UserService(val userRepository: UserRepository, val busStopsRepository: BusStopsRepository) {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    fun setFavouriteStopsFor(userId: Long, stopIds: MutableList<Long>) {
        logger.info("Want to set ${stopIds.size} favourite stops for user id $userId")
        if (userRepository.existsById(userId)) {
            val userDAO: UserDAO = this.getUserDAOById(userId)!!

            val listOfNonExistingBusStopIds: MutableList<Long> = mutableListOf<Long>()
            val newFavouriteListOfBusStopDAOs: MutableList<BusStopDAO> = mutableListOf<BusStopDAO>()

            for (stopId: Long in stopIds) {
                if (busStopsRepository.existsById(stopId)) {
                    newFavouriteListOfBusStopDAOs.add(busStopsRepository.findByIdOrNull(stopId)!!)
                } else {
                    listOfNonExistingBusStopIds.add(stopId)
                    logger.error("Bus Stop with id $stopId does not exist")
                }
            }

            if (listOfNonExistingBusStopIds.isEmpty()) {
                userDAO.favouriteBusStopsDAOS.clear()
                userDAO.favouriteBusStopsDAOS.addAll(newFavouriteListOfBusStopDAOs)
                try {
                    userRepository.save(userDAO)
                    print(20 / 0)
                } catch (e: Exception) {
                    logger.error("Cannot save favourites in DB for $userDAO")
                    throw Exception("Cannot save favourites in DB for $userDAO : ${e.message}")
                }
            } else {
                // There are unknown BusStopIds
                throw NotFoundException(
                    "Didn't change favourite stops for userId $userId, as some busStopIds were not known [${
                        listOfNonExistingBusStopIds.joinToString(
                            separator = ", "
                        )
                    }]"
                )
            }
        } else {
            // UserId does not exist
            logger.error("User with id $userId does not exist")
            throw NotFoundException("User with id $userId does not exist")
        }
    }

    fun getUserByName(name: String): User? {
        if (userRepository.existsByName(name)) {
            return (User(userRepository.getByName(name)))
        }
        return null
    }

    fun getFavouriteStops(userId: Long): MutableList<BusStop> {
        logger.info("Want to retrieve favourite stops for userId $userId")
        val myUser: User? = this.getUserById(userId)
        if (myUser == null) {
            logger.error("User with id $userId does not exist")
            return mutableListOf<BusStop>()
        } else {
            return myUser.favouriteBusStops
        }
    }

    fun getUserById(userId: Long): User? {
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
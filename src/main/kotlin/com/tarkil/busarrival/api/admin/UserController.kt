package com.tarkil.busarrival.api.admin

import com.tarkil.busarrival.domain.bus.BusStop
import com.tarkil.busarrival.domain.user.User
import com.tarkil.busarrival.domain.user.UserService
import feign.FeignException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.webjars.NotFoundException


@RestController
@RequestMapping("/admin/users")
class UserController(val userService: UserService) {

    @GetMapping("")
    fun getUsers(): ResponseEntity<MutableList<User>> {
        return ResponseEntity(userService.getAll(), HttpStatus.OK)
    }

    @GetMapping("/{userId}")
    fun getUser(@PathVariable("userId") userId: Long): ResponseEntity<Any> {
        val user: User?
        user = userService.getUserById(userId)
        if (user == null) {
            return ResponseEntity("User with id ${userId.toString()} does not exist", HttpStatus.NOT_FOUND)
        } else {
            return ResponseEntity(user, HttpStatus.OK)
        }
    }

    @PostMapping("")
    fun newUser(@RequestParam(value = "name") name: String): ResponseEntity<String> {
        var response: ResponseEntity<String>
        try {
            val newUserId = userService.insertNewUser(User(userName = name))
            response = ResponseEntity("Done : id $newUserId", HttpStatus.OK)
        } catch (e: FeignException.NotFound) {
            response = ResponseEntity(e.message, HttpStatus.NOT_FOUND)
        } catch (e: Exception) {
            response = ResponseEntity(e.message, HttpStatus.INTERNAL_SERVER_ERROR)
        }

        return (response)
    }

    @GetMapping("favourite-stops")
    fun getFavouriteStopsForUser(@RequestParam(value = "userId") userId: Long): ResponseEntity<Any> {
        val busStops: MutableList<BusStop>
        val user: User? = userService.getUserById(userId)
        if (user == null) {
            return ResponseEntity("User with id $userId does not exist", HttpStatus.NOT_FOUND)
        } else {
            return (ResponseEntity(user.getFavouriteBusStopsAsAPIResponse(), HttpStatus.OK))
        }
    }

    @PostMapping("favourite-stops")
    fun addNewFavouriteStopForUser(
        @RequestParam(value = "userId") userId: Long,
        @RequestParam(value = "busStopId") busStopId: Long,
        @RequestParam(value = "serviceNo") serviceNo: String
    ): ResponseEntity<String> {
        try {
//            userService.setFavouriteStopsFor(userId, stopIds)
            userService.addNewFavouriteStopFor(userId, busStopId, serviceNo)
            return ResponseEntity(
                "Successfully added {busStopId=$busStopId,serviceNo=$serviceNo} in favourites of userId=$userId",
                HttpStatus.OK
            )
        } catch (e: NotFoundException) {
            return ResponseEntity("${e.message}", HttpStatus.NOT_FOUND)
        } catch (e: Exception) {
            return ResponseEntity("Cannot set favourite stops : ${e.message}", HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}
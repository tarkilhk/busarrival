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
            return (ResponseEntity(user.favouriteBusStops, HttpStatus.OK))
        }
    }

    @PostMapping("favourite-stops")
    fun setFavouriteStopsForUser(
        @RequestParam(value = "userId") userId: Long,
        @RequestParam(value = "stopIds") stopIds: MutableList<Long>
    ): ResponseEntity<String> {
        var message: String = ""
        try {
            userService.setFavouriteStopsFor(userId, stopIds)
            return ResponseEntity("Successfully set ${stopIds.size} bus stops as favourites", HttpStatus.OK)
        } catch (e: NotFoundException) {
            return ResponseEntity("${e.message}", HttpStatus.NOT_FOUND)
        } catch (e: Exception) {
            return ResponseEntity("Cannot set favourite stops : ${e.message}", HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}
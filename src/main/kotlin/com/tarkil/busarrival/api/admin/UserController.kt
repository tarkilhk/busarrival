package com.tarkil.busarrival.api.admin

import com.tarkil.busarrival.domain.bus.BusStop
import com.tarkil.busarrival.domain.user.User
import com.tarkil.busarrival.domain.user.UserService
import feign.FeignException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/admin/users")
class UserController(val userService: UserService) {

    @GetMapping("")
    fun getUsers(): MutableList<User> {
        return userService.getAll()
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
    fun getFavouriteStopsForUser(@RequestParam(value = "userId") userId: Long): MutableList<BusStop> {
        return userService.getFavouriteStops(userId = userId)
    }

    @PostMapping("favourite-stops")
    fun setFavouriteStopsForUser(
        @RequestParam(value = "userId") userId: Long,
        @RequestParam(value = "stopIds") stopIds: MutableList<Long>
    ): String {
        userService.setFavouriteStopsFor(userId, stopIds)
        return ("Done")
    }
}
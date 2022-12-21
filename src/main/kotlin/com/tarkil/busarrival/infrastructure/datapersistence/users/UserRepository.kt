package com.tarkil.busarrival.infrastructure.datapersistence.users

import org.springframework.data.repository.CrudRepository


interface UserRepository : CrudRepository<UserDAO, Long> {

    fun getByName(name: String): UserDAO

    fun existsByName(name: String): Boolean
}
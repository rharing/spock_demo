package com.roha.example.spock.demo.dao

import com.roha.example.spock.demo.model.Event
import org.joda.time.DateTime
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface EventRepository extends CrudRepository<Event,Long> {


}
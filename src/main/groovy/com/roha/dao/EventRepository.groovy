package com.roha.dao

import com.roha.model.Event
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface EventRepository extends CrudRepository<Event,Long> {


}

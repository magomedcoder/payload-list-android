package ru.magomedcoder.askue.data.remote.response

import ru.magomedcoder.askue.data.remote.dto.EventDto
import ru.magomedcoder.askue.domain.model.Event

data class EventCounterResponse(
    val alertscount_perday: EventDto
) {

    fun toEventResponse(): Event {
        return Event(
            orangeLevel = alertscount_perday.orange_level,
            redLevel = alertscount_perday.red_level,
            yellowLevel = alertscount_perday.yellow_level
        )
    }

}
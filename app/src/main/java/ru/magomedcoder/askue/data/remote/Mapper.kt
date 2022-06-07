package ru.magomedcoder.askue.data.remote

import ru.magomedcoder.askue.data.remote.dto.ElectronicArchiveDto
import ru.magomedcoder.askue.data.remote.dto.ElectronicCounterDto
import ru.magomedcoder.askue.data.remote.dto.ElectronicEventDto
import ru.magomedcoder.askue.data.remote.dto.ElectronicOutDto
import ru.magomedcoder.askue.domain.model.*

fun ElectronicCounterDto.toElectronicCounterDomain(): ElectronicCounter {
    return ElectronicCounter(
        devEui = dev_eui,
        serN = SerN,
        placeAddress = PlaceAddress(
            city = place_address.city,
            street = place_address.street,
            unit = place_address.unit,
            number = place_address.number,
        ),
        lastSeenAt = last_seen_at,
        personalAccount = personal_account
    )
}

fun ElectronicEventDto.toElectronicEventDomain(): ElectronicEvent {
    return ElectronicEvent(
        id = id,
        alarmReset = alarm_reset,
        dateTime = date_time,
        event = Event(
            orangeLevel = event.orange_level,
            redLevel = event.red_level,
            yellowLevel = event.yellow_level
        ),
        modelOfDevice = model_of_device,
        placeAddress = PlaceAddress(
            city = place_address.city,
            street = place_address.street,
            unit = place_address.unit,
            number = place_address.number,
        )
    )
}

fun ElectronicArchiveDto.toElectronicArchiveDomain(): ElectronicArchive {
    return ElectronicArchive(
        id = id,
        alarmReset = alarm_reset,
        dateTime = date_time,
        event = Event(
            orangeLevel = event.orange_level,
            redLevel = event.red_level,
            yellowLevel = event.yellow_level
        ),
        modelOfDevice = model_of_device,
        placeAddress = PlaceAddress(
            city = place_address.city,
            street = place_address.street,
            unit = place_address.unit,
            number = place_address.number,
        )
    )
}

fun ElectronicOutDto.toElectronicOutDomain(): ElectronicOut {
    return ElectronicOut(
        serN = SerN,
        devEui = dev_eui,
        lastSeenAt = last_seen_at,
        personalAccount = personal_account,
        placeAddress = PlaceAddress(
            city = place_address.city,
            street = place_address.street,
            unit = place_address.unit,
            number = place_address.number,
        ),
        sequenceNumber = sequence_number
    )
}
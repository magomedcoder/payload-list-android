package ru.magomedcoder.askue.data.remote

import ru.magomedcoder.askue.data.remote.dto.ElectronicCounterDto
import ru.magomedcoder.askue.domain.model.ElectronicCounter

fun ElectronicCounterDto.toElectronicCounterDomain(): ElectronicCounter {
    return ElectronicCounter(
        devEui = dev_eui,
        serN = SerN,
        placeAddress = ElectronicCounter.PlaceAddress(
            city = place_address.city,
            street = place_address.street,
            unit = place_address.unit,
            number = place_address.number,
        ),
        lastSeenAt = last_seen_at,
        personalAccount = personal_account
    )
}
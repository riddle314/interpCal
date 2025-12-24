package com.dimitriskatsikas.interpolator

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object Calculator : Route

    @Serializable
    data object Info : Route
}

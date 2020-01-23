package mqtt.packets

import mqtt.MQTTException

enum class Qos(val value: Int) {
    AT_MOST_ONCE(0),
    AT_LEAST_ONCE(1),
    EXACTLY_ONCE(2);

    companion object {
        fun valueOf(value: Int) =
            values().firstOrNull { it.value == value }
                ?: throw MQTTException(ReasonCode.MALFORMED_PACKET) // TODO maybe protocol error
    }
}
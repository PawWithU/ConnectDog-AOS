package com.kusitms.connectdog.feature.home.model

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalDate

@Serializable
data class Filter(
    var departure: String = "",
    var arrival: String = "",
    @Serializable(with = LocalDateSerializer::class)
    var startDate: LocalDate? = null,
    @Serializable(with = LocalDateSerializer::class)
    var endDate: LocalDate? = null,
    var detail: Detail = Detail(),
) {
    fun isNotEmpty(): Boolean {
        return departure.isNotEmpty() || arrival.isNotEmpty() || startDate != null || endDate != null || detail.isNotEmpty()
    }
}

@Serializable
data class Detail(
    val dogSize: DogSize? = null,
    val hasKennel: Boolean? = null,
    val organization: String? = null,
) {
    fun isNotEmpty(): Boolean {
        return dogSize != null || hasKennel != null || organization != null
    }

    @Serializable
    enum class DogSize {
        BIG,
        MIDDLE,
        SMALL,
        ;

        fun toDisplayName(): String {
            return when (this) {
                BIG -> "대형"
                MIDDLE -> "중형"
                SMALL -> "소형"
            }
        }
    }
}

object LocalDateSerializer : KSerializer<LocalDate> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("LocalDate", PrimitiveKind.STRING)

    override fun serialize(
        encoder: Encoder,
        value: LocalDate,
    ) {
        encoder.encodeString(value.toString())
    }

    override fun deserialize(decoder: Decoder): LocalDate {
        return LocalDate.parse(decoder.decodeString())
    }
}

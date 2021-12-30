package com.dyurekdeler.OnlineMovieStoreInventory.serializer

import com.dyurekdeler.OnlineMovieStoreInventory.model.kafka.*
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.common.serialization.Deserializer
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class KafkaModelDeserializer(
    private val mapper: ObjectMapper
): Deserializer<Any> {


    private val logger = LoggerFactory.getLogger(javaClass)

    override fun configure(configs: MutableMap<String, *>?, isKey: Boolean) {
        super.configure(configs, isKey)
    }

    override fun deserialize(topic: String?, data: ByteArray?): Any? {
        if(data == null){
            return null
        }
        return when(topic){
            "payment_events" -> {
                mapper.readValue(data, PaymentCreatedEvent::class.java)
            }
            "delivery_fails" -> {
                mapper.readValue(data, DeliveryFailedEvent::class.java)
            }
            else -> null
        }


    }

    override fun close() {
        super.close()
    }
}
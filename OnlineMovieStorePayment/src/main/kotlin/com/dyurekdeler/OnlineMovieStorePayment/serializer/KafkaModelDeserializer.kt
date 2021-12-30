package com.dyurekdeler.OnlineMovieStorePayment.serializer

import com.dyurekdeler.OnlineMovieStorePayment.model.kafka.InventoryUpdatedEvent
import com.dyurekdeler.OnlineMovieStorePayment.model.kafka.OrderCreatedEvent
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.common.serialization.Deserializer
import org.springframework.stereotype.Service

@Service
class KafkaModelDeserializer(
    private val mapper: ObjectMapper
): Deserializer<Any> {

    override fun configure(configs: MutableMap<String, *>?, isKey: Boolean) {
        super.configure(configs, isKey)
    }

    override fun deserialize(topic: String?, data: ByteArray?): Any? {
        if(data == null){
            return null
        }
        return when(topic){
            "order_events" -> {
                mapper.readValue(data, OrderCreatedEvent::class.java)
            }
            "inventory_fails" -> {
                mapper.readValue(data, InventoryUpdatedEvent::class.java)
            }
            else -> null
        }


    }

    override fun close() {
        super.close()
    }
}
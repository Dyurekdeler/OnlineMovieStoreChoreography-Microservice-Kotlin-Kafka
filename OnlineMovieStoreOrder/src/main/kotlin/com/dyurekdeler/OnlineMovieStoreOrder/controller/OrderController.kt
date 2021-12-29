package com.dyurekdeler.OnlineMovieStoreOrder.controller

import com.dyurekdeler.OnlineMovieStoreOrder.entity.Order
import com.dyurekdeler.OnlineMovieStoreOrder.request.OrderRequest
import com.dyurekdeler.OnlineMovieStoreOrder.service.KafkaService
import com.dyurekdeler.OnlineMovieStoreOrder.service.OrderService
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.clients.producer.RecordMetadata
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.util.concurrent.ListenableFuture
import org.springframework.web.bind.annotation.*
import java.util.concurrent.Future

@RestController
class OrderController(
    private val orderService: OrderService,
    private val kafkaService: KafkaService
) {



    @GetMapping("/{id}")
    fun getOrder(@PathVariable("id") id: String): ResponseEntity<Order> {
        val order = orderService.findById(id)
        return ResponseEntity.ok(order)
    }

    @PostMapping("/placeOrder")
    fun placeOrder(@RequestBody request: OrderRequest): ResponseEntity<Order> {
        val order = orderService.placeOrder(request)
        return ResponseEntity(order, HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    fun updateOrder(@RequestBody request: OrderRequest, @PathVariable("id") id: String): Order {
        val updatedOrder = orderService.updateOrder(id, request)
        return updatedOrder
    }

    @DeleteMapping("/{id}")
    fun deleteOrder(@PathVariable("id") id: String) {
        orderService.deleteById(id)
    }


    @PostMapping("/produce")
    fun produceMessage(@RequestBody message : String) : ResponseEntity<String> {
        /*
        val topic:String = "test_topic"
        var producerRecord : ProducerRecord<String, String> = ProducerRecord(topic, message)
        val map = mutableMapOf<String, String>()
        map["key.serializer"]   = "org.apache.kafka.common.serialization.StringSerializer"
        map["value.serializer"] = "org.apache.kafka.common.serialization.StringSerializer"
        map["bootstrap.servers"] = "localhost:9092"
        var producer = KafkaProducer<String, String>(map as Map<String, Any>?)
        var future: Future<RecordMetadata> = producer?.send(producerRecord)!!
        return ResponseEntity.ok(" message sent to " + future.get().topic())
         */
        kafkaService.produceTestMessage(message)
        return ResponseEntity.ok(" message sent to test topic")
    }





}
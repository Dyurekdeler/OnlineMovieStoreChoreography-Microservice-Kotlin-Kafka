package com.dyurekdeler.OnlineMovieStoreOrder.controller

import com.dyurekdeler.OnlineMovieStoreOrder.entity.Order
import com.dyurekdeler.OnlineMovieStoreOrder.request.OrderRequest
import com.dyurekdeler.OnlineMovieStoreOrder.service.OrderService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class OrderController(
    private val orderService: OrderService,
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


    // kafka

    var kafkaTemplate: KafkaTemplate<String, String>? = null;
    val topic:String = "test_topic"
    @PostMapping("/send")
    fun sendMessage(@RequestBody message : String) : ResponseEntity<String> {
        var lf : ListenableFuture<SendResult<String, String>> = kafkaTemplate?.send(topic, message)!!
        var sendResult: SendResult<String, String> = lf.get()
        return ResponseEntity.ok(sendResult.producerRecord.value() + " sent to topic")
    }

    // second way to produce kafka msg
    @PostMapping("/produce")
    fun produceMessage(@RequestBody message : String) : ResponseEntity<String> {
        var producerRecord : ProducerRecord<String, String> = ProducerRecord(topic, message)
        val map = mutableMapOf<String, String>()
        map["key.serializer"]   = "org.apache.kafka.common.serialization.StringSerializer"
        map["value.serializer"] = "org.apache.kafka.common.serialization.StringSerializer"
        map["bootstrap.servers"] = "localhost:9092"
        var producer = KafkaProducer<String, String>(map as Map<String, Any>?)
        var future: Future<RecordMetadata> = producer?.send(producerRecord)!!
        return ResponseEntity.ok(" message sent to " + future.get().topic());
    }

    @KafkaListener(topics= ["test_topic"], groupId = "test_id")
    fun consume(message:String) :Unit {
        logger.info("Deniz message received from topic : $message");
    }

}
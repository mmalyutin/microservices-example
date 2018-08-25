package io.microservicessample.itemservice

import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.BodyInserters.fromObject
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse

class ItemHandler(private val itemRepository: ItemRepository) {

    @Suppress("UNUSED_PARAMETER")
    fun getAllItems(request: ServerRequest) = ServerResponse.ok()
            .contentType(APPLICATION_JSON)
            .body(fromObject(itemRepository.findAll()))

    fun getItem(request: ServerRequest) = ServerResponse.ok()
            .contentType(APPLICATION_JSON)
            .body(fromObject(itemRepository.findById(request.pathVariable("id").toLong()) ?: Exception("")))

    fun updateItem(request: ServerRequest) = request.bodyToMono(Item::class.java).flatMap(::saveAndRespond)

    fun addItem(request: ServerRequest) = request.bodyToMono(Item::class.java).flatMap(::saveAndRespond)

    private fun saveAndRespond(item: Item) = ServerResponse.ok()
            .contentType(APPLICATION_JSON)
            .body(fromObject(itemRepository.save(item)))
}
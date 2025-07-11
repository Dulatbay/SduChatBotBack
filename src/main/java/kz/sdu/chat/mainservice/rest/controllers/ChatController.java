package kz.sdu.chat.mainservice.rest.controllers;

import kz.sdu.chat.mainservice.constants.Utils;
import kz.sdu.chat.mainservice.rest.dto.request.ChatCreateRequest;
import kz.sdu.chat.mainservice.rest.dto.request.MessageCreateRequest;
import kz.sdu.chat.mainservice.rest.dto.response.ChatResponse;
import kz.sdu.chat.mainservice.rest.dto.base.PaginatedResponse;
import kz.sdu.chat.mainservice.rest.dto.base.PaginationParams;
import kz.sdu.chat.mainservice.rest.dto.response.MessageResponse;
import kz.sdu.chat.mainservice.rest.dto.response.SendMessageResponse;
import kz.sdu.chat.mainservice.services.ChatService;
import kz.sdu.chat.mainservice.services.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/chats")
@RestController
@PreAuthorize("isAuthenticated()")
public class ChatController {
    private final ChatService chatService;
    private final MessageService messageService;

    @GetMapping("/connected")
    public ResponseEntity<Boolean> getConnectedChats() {
        return ResponseEntity.ok(Optional.ofNullable(chatService.getConnectionStatus()).isPresent());
    }

    @GetMapping
    public ResponseEntity<PaginatedResponse<ChatResponse>> getChat(@ModelAttribute PaginationParams paginationParams) {
        var user = Utils.getCurrentUser();
        var chats = chatService.getChats(user, paginationParams);
        return ResponseEntity.ok(new PaginatedResponse<>(chats));
    }

    @GetMapping("/{chatId}")
    public ResponseEntity<ChatResponse> getChatById(@PathVariable long chatId) {
        var user = Utils.getCurrentUser();
        log.info("Fetching chat with ID: {}", chatId);
        var chatResponse = chatService.getChatById(chatId, user);
        return ResponseEntity.ok(chatResponse);
    }

    @PostMapping
    public ResponseEntity<Void> createChat(@RequestBody ChatCreateRequest chatCreateRequest) {
        var user = Utils.getCurrentUser();

        log.info("Creating chat with title: {}", chatCreateRequest.getTitle());
        chatService.createChat(chatCreateRequest, user);
        return ResponseEntity.status(201).build();
    }

    @DeleteMapping("/{chatId}")
    public ResponseEntity<Void> deleteChat(@PathVariable long chatId) {
        var user = Utils.getCurrentUser();
        log.info("Deleting chat with ID: {}", chatId);
        chatService.deleteChat(chatId, user);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{chatId}/messages")
    public ResponseEntity<PaginatedResponse<MessageResponse>> getMessages(@PathVariable long chatId, @ModelAttribute PaginationParams paginationParams) {
        log.info("Fetching messages for chat with ID: {}", chatId);
        var user = Utils.getCurrentUser();
        var messages = messageService.getMessagesForChat(chatId, user, paginationParams);
        return ResponseEntity.ok(new PaginatedResponse<>(messages));
    }

    @PostMapping("/{chatId}/messages")
    public ResponseEntity<SendMessageResponse> sendMessage(@PathVariable long chatId, @RequestBody MessageCreateRequest messageCreateRequest) {
        var user = Utils.getCurrentUser();
        log.info("Sending message to chat with ID: {}", chatId);
        return ResponseEntity.ok(messageService.sendMessage(chatId, messageCreateRequest, user));
    }

    @PostMapping("/send-message")
    public ResponseEntity<SendMessageResponse> sendMessageToChat(@RequestBody MessageCreateRequest messageCreateRequest) {
        var user = Utils.getCurrentUser();
        log.info("Sending message to chat with content: {}", messageCreateRequest.getContent());
        return ResponseEntity.ok(messageService.createChatAndSendMessage(messageCreateRequest, user));
    }
}


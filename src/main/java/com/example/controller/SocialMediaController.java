package com.example.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
@RequestMapping("/")
public class SocialMediaController {
    private AccountService accountService;
    private MessageService messageService;
    
    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("/register")
    public @ResponseBody ResponseEntity<Account> createAccount( @RequestBody Account newAccount){
        Integer newUserNameLength = newAccount.getUsername().length();
        Integer newPasswordLength = newAccount.getPassword().length();
        if(newUserNameLength > 0 && newPasswordLength > 4 && !accountService.doesUserNameExist(newAccount.getUsername())){
            Account verifiedAccount = accountService.registerAccount(newAccount);
            return ResponseEntity.status(200).body(verifiedAccount);
        }
        else if(accountService.doesUserNameExist(newAccount.getUsername())){
            return ResponseEntity.status(409).body(null);

        }
        else return ResponseEntity.status(400).body(null);
        
    }    

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<Account> verifyAccount(@RequestBody Account accountToVerify){
        String usernameToVerify = accountToVerify.getUsername();
        String passwordToVerify = accountToVerify.getPassword();
        if(accountService.doesUserNameExist(usernameToVerify) == true && accountService.doesPasswordExist(passwordToVerify) == true){
            return ResponseEntity.status(200).body(accountService.loginAccount(accountToVerify));
        }
        
        return ResponseEntity.status(401).body(null);
    }


    @PostMapping("/messages")
    @ResponseBody
    public  ResponseEntity<Message> createMessage(@RequestBody Message newMessage){
        Integer addedMessageLength = newMessage.getMessageText().length();
        Integer addeMessagePostedBy = newMessage.getPostedBy();
        //Check message length restictions and if the postedBy matches an accountId
        if(addedMessageLength > 0 && addedMessageLength < 255 && accountService.findAccountById(addeMessagePostedBy) != null){
            messageService.addMessage(newMessage);
            return ResponseEntity.status(200).body(newMessage);
        }
        return ResponseEntity.status(400).body(null);
    }

    @GetMapping("/messages")
    public @ResponseBody ResponseEntity <List<Message>> getAllMessages(){
        List<Message> allMessages = messageService.getAllMessages();
        return ResponseEntity.status(200).body(allMessages);
    }


    @GetMapping("/messages/{messageId}")
    public @ResponseBody ResponseEntity <Message> getMessageById(@PathVariable ("messageId") Integer messageId){
        Message retrievedMessage = messageService.getMessageById(messageId);
        return ResponseEntity.status(200).body(retrievedMessage);
    }
        

    
    @DeleteMapping("/messages/{messageId}")
    public @ResponseBody ResponseEntity<Integer> deleteMessage(@PathVariable ("messageId") Integer messageId){
         if(messageService.getMessageById(messageId) != null){
            messageService.deleteMessage(messageId);
            return ResponseEntity.status(200).body(messageService.deleteMessage(messageId));
         }
         return ResponseEntity.status(200).body(null);
    }
    

    @PatchMapping("/messages/{messageId}")
    @ResponseBody
    public  ResponseEntity<Integer> updateMessageText(
        @PathVariable ("messageId") Integer messageId, 
         @RequestBody String messageText){
            if(messageService.getMessageById(messageId) != null && 
            messageText.length() > 0 && 
            messageText.length() < 255){
                messageService.updateMessageText(messageId, messageText);
                return ResponseEntity.status(200).body(messageService.updateMessageText(messageId, messageText));
            }
        return ResponseEntity.status(400).body(0);

    }


    @GetMapping("accounts/{accountId}/messages")
    public @ResponseBody ResponseEntity <List<Message>> getAllMessagesByAccount(@PathVariable ("accountId") Integer accountId){
        List<Message> allMessagesByAccount = messageService.getMessagesByAccount(accountId);
        return ResponseEntity.status(200).body(allMessagesByAccount);
    }

}
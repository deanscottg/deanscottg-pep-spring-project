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

    // @PostMapping("/register")
    // public ResponseEntity<String> createAccount(
    //         @RequestParam String username,
    //         @RequestParam String password
    // ){
    //     List<Account> accountList = accountRepository.findAll();
    //     if(username.length() > 0 && password.length() > 0 && password.length() < 255){
    //         accountList.add(new Account(username,password));
            
    //         return ResponseEntity.status(200).body("Account created successfully");
    //     }
    //     // Check if userName already exists
    //     else if(accountList.accountRepository.existsByUsername(username)){
    //         return ResponseEntity.status(409).body("Username already exists");
    //     }
    //     else return ResponseEntity.status(400).body("Some other error");
        
    // }
    

    @PostMapping("/login")
    public Account verifyAccount(@RequestBody Account verifiedAccount){
        return verifiedAccount;
    }


    @PostMapping("/messages")
    public @ResponseBody ResponseEntity<Message> createMessage(@RequestBody Message newMessage){
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
    public @ResponseBody ResponseEntity <Message> getMessageById(@PathVariable Integer messageId){
        Message retrievedMessage = messageService.getMessageById(messageId);
        return ResponseEntity.status(200).body(retrievedMessage);
    }
        

    
    @DeleteMapping("/messages/{messageId}")
    public @ResponseBody ResponseEntity<String> deleteMessage(@PathVariable Integer messageId){
         if(messageService.getMessageById(messageId) != null){
            messageService.deleteMessage(messageId);
            return ResponseEntity.status(200).body(null);
         }
         return ResponseEntity.status(200).body(null);
    }
    

    @PatchMapping("/messages/{messageId}")
    public @ResponseBody ResponseEntity<String> updateMessageText(@PathVariable Integer messageId, @RequestParam String messageText){
        if(messageService.getMessageById(messageId) != null && messageText.length() > 0 && messageText.length() < 255){
            messageService.updateMessageText(messageId, messageText);
            return ResponseEntity.status(200).body(null);
        }
        return ResponseEntity.status(400).body("Message cannot be created");

    }


    @GetMapping("accounts/{accountId}/messages")
    public @ResponseBody ResponseEntity <List<Message>> getAllMessagesByAccount(PathVariable accountId){
        List<Message> allMessagesByAccount = messageService.(accountId);
        return ResponseEntity.status(200).body(allMessagesByAccount);
    }




}
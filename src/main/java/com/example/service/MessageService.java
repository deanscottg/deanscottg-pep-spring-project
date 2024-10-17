package com.example.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
@Transactional
public class MessageService {
    private MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    public Message addMessage(Message message){
        return messageRepository.save(message);
    }
   
    public List<Message> getAllMessages(){
        return  (List<Message>) messageRepository.findAll();
    }

    public Message getMessageById(Integer messageId) {
        Optional<Message> optionalMessage = messageRepository.findById(messageId);
        if(optionalMessage.isPresent()){
            return optionalMessage.get();
        }
        else return null;

    }

    public Integer deleteMessage(Integer messageId){
        Optional <Message> messageToDelete = messageRepository.findById(messageId);
        if(messageToDelete.isPresent()){
            messageRepository.deleteById(messageId);
        }
        return 1;
    }

    public Integer updateMessageText(Integer messageId, Message messageToUpdate){
        Optional<Message> optionalMessage = messageRepository.findById(messageId);
        if(optionalMessage.isPresent() && 
            messageToUpdate.getMessageText().length() < 255 && 
            messageToUpdate.getMessageText() != ""){
                Message updatedMessage = optionalMessage.get();
                updatedMessage.setMessageText(messageToUpdate.getMessageText());
                messageRepository.save(updatedMessage);
                return 1;
                }
            else return null;
        }

    public List<Message> getMessagesByAccount(Integer accountId){
        List<Message> messagesByAccount = messageRepository.findByPostedBy(accountId);
        return messagesByAccount;
    }

    
}

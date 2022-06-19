package com.miola.messages;

import com.miola.dto.ContactRequest;
import com.miola.responseMessages.UtilMessages;
import com.miola.users.UserModel;
import com.miola.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    public void contact(ContactRequest contactRequest) {

        List<UserModel> admins = userService.getAllByRole(UtilMessages.ADMIN);
        String[] adminsEmails = admins.stream().map(UserModel::getEmail).toArray(String[]::new);

        emailService.sendSimpleMessage(adminsEmails, contactRequest.getSubject(), contactRequest.getMessage(), contactRequest.getEmail());
    }

    public void addMessage(MessageModel messageModel) {
        messageRepository.save(messageModel);
    }
}

package com.miola.messages;

import com.miola.dto.ContactRequest;
import com.miola.responseMessages.UtilMessages;
import com.miola.users.UserModel;
import com.miola.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    public void contact(ContactRequest contactRequest) {

        Optional<UserModel> userOp = userService.getOneByRole(UtilMessages.ADMIN);

        UserModel user = null;

        if (userOp.isPresent()) {
            user = userOp.get();
        }

        emailService.sendSimpleMessage(user.getEmail(), contactRequest.getSubject(), contactRequest.getMessage());
    }
}

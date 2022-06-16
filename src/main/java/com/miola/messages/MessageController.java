package com.miola.messages;

import com.miola.dto.BasicResponse;
import com.miola.dto.ContactRequest;
import com.miola.responseMessages.ControllerMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("")
    public ResponseEntity<BasicResponse> contact(@RequestBody @Validated ContactRequest contactReq) {
        messageService.contact(contactReq);

        BasicResponse response = new BasicResponse(HttpStatus.CREATED, ControllerMessages.SUCCESS);
        return new ResponseEntity<>(response,response.getStatus());
    }

}

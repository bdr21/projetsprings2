package com.miola.messages;

import com.miola.dto.BasicResponse;
import com.miola.dto.ContactRequest;
import com.miola.responseMessages.ControllerMessages;
import com.miola.responseMessages.ExceptionMessages;
import com.miola.users.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("")
    public ResponseEntity<BasicResponse> contact(@RequestBody @Validated ContactRequest contactReq) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String fn = null;
        String ln = null;
        String email = null;
        if (auth!= null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
            CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
            fn = userDetails.getFirstName();
            ln = userDetails.getLastName();
            email = userDetails.getEmail();
            contactReq.setFirstName(fn);
            contactReq.setLastName(ln);
            contactReq.setEmail(email);
        } else {
            fn = contactReq.getFirstName();
            ln = contactReq.getLastName();
            email = contactReq.getEmail();
        }
        if (fn == null || ln == null || email == null) {
            return new ResponseEntity<>(new BasicResponse(HttpStatus.BAD_REQUEST, ControllerMessages.EMAIL_FN_LN_REQUIRED), HttpStatus.BAD_REQUEST);
        }
        messageService.addMessage(new MessageModel(fn, ln, email, contactReq.getSubject(), contactReq.getMessage()));
        messageService.contact(contactReq);

        BasicResponse response = new BasicResponse(HttpStatus.CREATED, ControllerMessages.SUCCESS);
        return new ResponseEntity<>(response,response.getStatus());
    }

}

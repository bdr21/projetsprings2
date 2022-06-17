package com.miola.endroit_crud.Controller;


import com.example.endroit_crud.Models.ResponseResul;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/upload")
public class UploadController {

    String IMAGE_FOLDER = "./src/main/resources/images/";

    @PostMapping("")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile uploadfile) {

        ResponseResul rr = new ResponseResul();

        if (uploadfile.isEmpty()) {
            return ResponseEntity.ok().body("please select a file!");
        }

        try {
            String[] fileUrls = saveUploadedFiles(Arrays.asList(uploadfile));
            rr.setMessage(fileUrls[0]);
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }

        rr.setstatusCode(200);
        return ResponseEntity.ok(rr);
    }

    //save file
    private String[] saveUploadedFiles(List<MultipartFile> files) throws IOException {
        String[] fileUrls = new String[files.size()];
        int index = 0;
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                continue;
            }

            byte[] bytes = file.getBytes();
            long TICKS_AT_EPOCH = 621355968000000000L;
            long tick = System.currentTimeMillis()*10000 + TICKS_AT_EPOCH;
            String filename = String.valueOf(tick).concat("_").concat(file.getOriginalFilename());

            Path path = Paths.get(IMAGE_FOLDER+filename);
            Files.write(path, bytes);
            fileUrls[index] = getBaseEnvLinkURL() + "/images/"+filename;
            index++;
        }
        return fileUrls;
    }

    protected String getBaseEnvLinkURL() {
        String baseEnvLinkURL=null;
        HttpServletRequest currentRequest =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        baseEnvLinkURL = "http://" + currentRequest.getLocalName();
        if(currentRequest.getLocalPort() != 80) {
            baseEnvLinkURL += ":" + currentRequest.getLocalPort();
        }
        if(!StringUtils.isEmpty(currentRequest.getContextPath())) {
            baseEnvLinkURL += currentRequest.getContextPath();
        }
        return baseEnvLinkURL;
    }
}
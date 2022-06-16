package com.miola.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseWithRecordCount<T>{

    private HttpStatus status;
    private String message;
    int recordCount;
    private List<?> data;

}

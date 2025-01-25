package com.ecommerce.project.paylod;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//for any kind of message/status to user, including use in custom exception ahndlers in exception class
@Data
@NoArgsConstructor
@AllArgsConstructor
public class APIResponse {
    private String message;
    private boolean status;
}

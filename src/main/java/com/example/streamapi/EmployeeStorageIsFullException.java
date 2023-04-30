package com.example.streamapi;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class EmployeeStorageIsFullException extends RuntimeException{
}

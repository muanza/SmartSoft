package com.smartsoft.controller;

import com.smartsoft.dto.ApiResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/license")
public class LicenseController {

    @GetMapping("/status")
    public ResponseEntity<ApiResponseDto> status(@RequestParam("key") String key) {
        return ResponseEntity.ok(new ApiResponseDto("License " + key + " is valid"));
    }
}

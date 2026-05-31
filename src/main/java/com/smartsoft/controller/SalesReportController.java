package com.smartsoft.controller;

import com.smartsoft.dto.ApiResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports/sales")
public class SalesReportController {

    @GetMapping("/daily")
    public ResponseEntity<ApiResponseDto> dailySales() {
        return ResponseEntity.ok(new ApiResponseDto("Daily sales report generated"));
    }

    @GetMapping("/products")
    public ResponseEntity<ApiResponseDto> productsSold() {
        return ResponseEntity.ok(new ApiResponseDto("Products sold report generated"));
    }

    @GetMapping("/users")
    public ResponseEntity<ApiResponseDto> userPerformance() {
        return ResponseEntity.ok(new ApiResponseDto("User performance report generated"));
    }
}

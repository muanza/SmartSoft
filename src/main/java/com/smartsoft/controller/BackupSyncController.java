package com.smartsoft.controller;

import com.smartsoft.dto.ApiResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/system")
public class BackupSyncController {

    @PostMapping("/backup")
    public ResponseEntity<ApiResponseDto> triggerBackup() {
        return ResponseEntity.ok(new ApiResponseDto("Backup triggered"));
    }

    @GetMapping("/sync-status")
    public ResponseEntity<ApiResponseDto> syncStatus() {
        return ResponseEntity.ok(new ApiResponseDto("Sync status: OK"));
    }
}

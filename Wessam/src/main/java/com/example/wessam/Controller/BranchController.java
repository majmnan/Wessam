package com.example.wessam.Controller;

import com.example.wessam.Api.ApiResponse;
import com.example.wessam.DTO.IN.BranchDTOIn;
import com.example.wessam.DTO.OUT.BranchDTOOut;
import com.example.wessam.Model.User;
import com.example.wessam.Service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/branches")
@RequiredArgsConstructor
public class BranchController {

    private final BranchService branchService;

    //Auth: admin
    @GetMapping("/get-all")
    public ResponseEntity<List<BranchDTOOut>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(branchService.getAll());
    }

    //Auth: gym
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addBranch(@AuthenticationPrincipal User user, @RequestBody BranchDTOIn dto) {
        branchService.addBranch(user.getId(), dto);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Branch added successfully"));
    }

    //Auth: gym
    @PutMapping("/update/{branchId}")
    public ResponseEntity<ApiResponse> updateBranch(@AuthenticationPrincipal User user, @PathVariable Integer branchId, @RequestBody BranchDTOIn dto) {
        branchService.updateBranch(user.getId(), branchId, dto);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Branch updated successfully"));
    }

    //Auth: gym
    @DeleteMapping("/delete/{branchId}")
    public ResponseEntity<ApiResponse> deleteBranch(@AuthenticationPrincipal User user, @PathVariable Integer branchId) {
        branchService.deleteBranch(user.getId(), branchId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Branch deleted successfully"));
    }

    //Auth: gym
    @GetMapping("/get-gym")
    public ResponseEntity<List<BranchDTOOut>> getByGym(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(HttpStatus.OK).body(branchService.getByGym(user.getId()));
    }
}

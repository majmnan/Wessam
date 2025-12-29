package com.example.wessam.Controller;

import com.example.wessam.Api.ApiResponse;
import com.example.wessam.Model.Sport;
import com.example.wessam.Service.SportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/sport")
public class SportController {

    private final SportService sportService;

    //Auth: any
    @GetMapping("/get")
    public ResponseEntity<List<Sport>> getSports() {
        return ResponseEntity.status(200).body(sportService.getSports());
    }

    //Auth: Admin
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addSport(@RequestBody @Valid Sport sport) {
        sportService.addSport(sport);
        return ResponseEntity.status(200).body(new ApiResponse("Sport added successfully"));
    }

    //Auth: Admin
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateSport(@PathVariable Integer id, @RequestBody @Valid Sport sport) {
        sportService.updateSport(id, sport);
        return ResponseEntity.status(200).body(new ApiResponse("Sport updated successfully"));
    }

    //Auth: Admin
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteSport(@PathVariable Integer id) {
        sportService.deleteSport(id);
        return ResponseEntity.status(200).body(new ApiResponse("Sport deleted successfully"));
    }

    @GetMapping("/nutrition-tip/{sportId}")
    public ResponseEntity<?> getNutritionTip(@PathVariable Integer sportId) {
        return ResponseEntity.status(200).body(new ApiResponse(sportService.getNutritionTip(sportId)));
    }

    @GetMapping("/home-workout/{sportId}")
    public ResponseEntity<?> getHomeDrill(@PathVariable Integer sportId) {
        return ResponseEntity.status(200).body(new ApiResponse(sportService.getHomeWorkout(sportId)));
    }
}

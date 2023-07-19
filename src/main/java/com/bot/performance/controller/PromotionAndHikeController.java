package com.bot.performance.controller;

import com.bot.performance.model.ApiResponse;
import com.bot.performance.model.PromotionAndHike;
import com.bot.performance.serviceinterface.IPromotionAndHikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/eps/promotion/")
public class PromotionAndHikeController {
    @Autowired
    IPromotionAndHikeService iPromotionAndHikeService;

    @PostMapping("addPromotionAndHike")
    public ResponseEntity<ApiResponse> addPromotionAndHike(@RequestBody List<PromotionAndHike> promotionAndHikes) throws Exception {
        var result = iPromotionAndHikeService.addPromotionAndHike(promotionAndHikes);
        return  ResponseEntity.ok(ApiResponse.Ok(result));
    }
}

package com.hguxgkx.answer_backend.controller;

import com.hguxgkx.answer_backend.common.entity.R;
import com.hguxgkx.answer_backend.config.JwtConfigPropreties;
import com.hguxgkx.answer_backend.service.serviceImpl.PrizeServiceImpl;
import com.hguxgkx.answer_backend.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/prize")
public class PrizeController {
    @Autowired
    PrizeServiceImpl prizeService;

    @Autowired
    JwtConfigPropreties jwtConfigPropreties;

    @RequestMapping("/init")
    public R init(@RequestBody Map<String, Object> map){
        return prizeService.init((Integer) map.get("id"), (List<String>) map.get("list"));
    }

    @GetMapping()
    public R getQX(HttpServletRequest request,
                 HttpServletResponse response) throws IOException {
        return prizeService.getQX(
                JwtUtils.analysisToken(request,response,jwtConfigPropreties.getSignature())
        );
    }

    @PostMapping()
    public R extract(HttpServletRequest request,
                     HttpServletResponse response,
                     @RequestBody Map<String,Integer> map) throws IOException {
        return prizeService.extract(
                map.get("id"),
                JwtUtils.analysisToken(request,response,jwtConfigPropreties.getSignature())
        );
    }
}

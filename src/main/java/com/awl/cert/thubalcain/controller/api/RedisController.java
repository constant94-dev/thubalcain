package com.awl.cert.thubalcain.controller.api;

import com.awl.cert.thubalcain.controller.api.dto.UpdateRedisDTO;
import com.awl.cert.thubalcain.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/redis")
@RequiredArgsConstructor
public class RedisController {
    private final RedisService redisService;

    @PostMapping("/save")
    public String save(@RequestParam String key, @RequestParam String value) {
        redisService.save(key, value);
        return "Saved";
    }

    @GetMapping("/get")
    public String find(@RequestParam String key) {
        return (String) redisService.find(key);
    }

    @PutMapping("/update")
    public String update(@RequestBody UpdateRedisDTO updateDTO) {
        redisService.updateValue(updateDTO.getKey(), updateDTO.getValue());
        return "Updated";
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam String key) {
        if (redisService.hasKey(key)) {
            redisService.deleteValue(key);
            return "Deleted";
        }
        return "Not Deleted";
    }
}

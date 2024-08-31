package com.awl.cert.thubalcain.controller.api;

import com.awl.cert.thubalcain.controller.api.dto.UpdateRedisDTO;
import com.awl.cert.thubalcain.service.RedisCRUDTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/redis")
@RequiredArgsConstructor
public class RedisCRUDController {
    private final RedisCRUDTestService redisCRUDTestService;

    @PostMapping("/save")
    public String save(@RequestParam String key, @RequestParam String value) {
        redisCRUDTestService.save(key, value);
        return "Saved";
    }

    @GetMapping("/get")
    public String find(@RequestParam String key) {
        return (String) redisCRUDTestService.find(key);
    }

    @PutMapping("/update")
    public String update(@RequestBody UpdateRedisDTO updateDTO) {
        redisCRUDTestService.updateValue(updateDTO.getKey(), updateDTO.getValue());
        return "Updated";
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam String key) {
        if (redisCRUDTestService.hasKey(key)) {
            redisCRUDTestService.deleteValue(key);
            return "Deleted";
        }
        return "Not Deleted";
    }
}

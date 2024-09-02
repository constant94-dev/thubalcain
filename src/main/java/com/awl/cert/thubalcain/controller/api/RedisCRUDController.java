package com.awl.cert.thubalcain.controller.api;

import com.awl.cert.thubalcain.controller.dto.request.ViewUpdateRedisCRUD;
import com.awl.cert.thubalcain.service.RedisCRUDService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/redis")
@RequiredArgsConstructor
public class RedisCRUDController {
    private final RedisCRUDService redisCRUDService;

    @PostMapping("/save")
    public String save(@RequestParam String key, @RequestParam String value) {
        redisCRUDService.save(key, value);
        return "Saved";
    }

    @GetMapping("/get")
    public String find(@RequestParam String key) {
        return (String) redisCRUDService.find(key);
    }

    @PutMapping("/update")
    public String update(@RequestBody ViewUpdateRedisCRUD updateDTO) {
        redisCRUDService.updateValue(updateDTO.getKey(), updateDTO.getValue());
        return "Updated";
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam String key) {
        if (redisCRUDService.hasKey(key)) {
            redisCRUDService.deleteValue(key);
            return "Deleted";
        }
        return "Not Deleted";
    }
}

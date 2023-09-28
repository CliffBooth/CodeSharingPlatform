package platform.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import platform.businessLayer.CodeObj;
import platform.businessLayer.CodeService;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController()
@RequestMapping("api")
public class ApiController {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    @Autowired
    private CodeService codeService;
    @GetMapping("/code/{id}")
    public CodeObj getJsonCode(HttpServletResponse response, @PathVariable String id) {
        response.addHeader("Content-Type", "application/json");
        Optional<CodeObj> opt = codeService.getAndUpdate(id);
        return opt.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/code/latest")
    public List<CodeObj> getJsonLatest() {
        return codeService.findLatest();
    }

    @PostMapping("/code/new")
    public Map<String, String> addCode(@RequestBody Map<String, String> json) {
        String code = json.get("code");
        long time = Long.parseLong(json.get("time"));
        int views = Integer.parseInt(json.get("views"));
        LocalDateTime savedTime = LocalDateTime.now();
        String date = savedTime.format(formatter);
        String codeId = UUID.randomUUID().toString();
        String id = codeService.save(new CodeObj(codeId, code, date, savedTime, views, time)).getId();
        return Map.of("id", id);
    }
}

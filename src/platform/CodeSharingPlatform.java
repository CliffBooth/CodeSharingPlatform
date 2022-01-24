package platform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import platform.businessLayer.CodeObj;
import platform.businessLayer.CodeService;

import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@SpringBootApplication
@Controller
public class CodeSharingPlatform {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private CodeService codeService;

    public static void main(String[] args) {
        SpringApplication.run(CodeSharingPlatform.class, args);
    }

    @GetMapping("/code/{id}")
    public String code(Model model, @PathVariable String id) {
        Optional<CodeObj> opt = getFromDatabase(id);
        CodeObj res;
        res = opt.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAllAttributes(Map.of("codeObj", res));
        return "code";
    }

    @GetMapping("/code/latest")
    public String codeLatest(Model model) {
        List<CodeObj> res = codeService.findLatest();
        model.addAllAttributes(Map.of("codeObjList", res));
        return "latest";
    }

    @GetMapping("/code/new")
    public String create() {
        return "create";
    }

    @GetMapping("/api/code/{id}")
    @ResponseBody
    public CodeObj getJsonCode(HttpServletResponse response, @PathVariable String id) {
        response.addHeader("Content-Type", "application/json");
        Optional<CodeObj> opt = getFromDatabase(id);
        return opt.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/api/code/latest")
    @ResponseBody
    public List<CodeObj> getJsonLatest() {
        return codeService.findLatest();
    }

    @PostMapping("/api/code/new")
    @ResponseBody
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

    //this method probably should be in the service class
    private Optional<CodeObj> getFromDatabase(String id) {
        LocalDateTime currentTime = LocalDateTime.now();
        Optional<CodeObj> res = codeService.findById(id);
        if (res.isPresent()) {
            CodeObj codeObj = res.get();
            boolean deleted = false;
            if (codeObj.isHasViews()) {
                codeObj.setViews(codeObj.getViews() - 1);
                if (codeObj.getViews() < 0) {
                    codeService.deleteById(id);
                    deleted = true;
                }
            }
            if (!deleted) {
                if (codeObj.isHasTime()) {
                    long difference = Duration.between(codeObj.getSavedTime(), currentTime).toSeconds();
                    codeObj.setTime(codeObj.getInitialTime() - difference);
                    if (codeObj.getTime() < 0) {
                        codeService.deleteById(id);
                        deleted = true;
                    }
                }
            }
            if (!deleted) {
                codeService.save(codeObj);
            }
        }
        //return updated result
        return codeService.findById(id);
    }
}
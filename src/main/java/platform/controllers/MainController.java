package platform.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;
import platform.businessLayer.CodeObj;
import platform.businessLayer.CodeService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class MainController {
    @Autowired
    CodeService codeService;

    @GetMapping("/")
    public String main() {
        return "index";
    }

    @GetMapping("/code/{id}")
    public String code(Model model, @PathVariable String id) {
        Optional<CodeObj> opt = codeService.getAndUpdate(id);
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
}

package platform.businessLayer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import platform.persistanceLayer.CodeRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CodeService {
    private final CodeRepository codeRepository;

//    @Autowired
    public CodeService(CodeRepository codeRepository) {
        this.codeRepository = codeRepository;
    }

    public CodeObj save(CodeObj codeObj) {
        return codeRepository.save(codeObj);
    }

    public Optional<CodeObj> findById(String id) {
        return codeRepository.findById(id);
    }

    public Optional<CodeObj> getAndUpdate(String id) {
        LocalDateTime currentTime = LocalDateTime.now();
        Optional<CodeObj> res = codeRepository.findById(id);
        if (res.isPresent()) {
            CodeObj codeObj = res.get();
            boolean deleted = false;
            if (codeObj.isHasViews()) {
                codeObj.setViews(codeObj.getViews() - 1);
                if (codeObj.getViews() < 0) {
                    codeRepository.deleteById(id);
                    deleted = true;
                }
            }
            if (!deleted) {
                if (codeObj.isHasTime()) {
                    long difference = Duration.between(codeObj.getSavedTime(), currentTime).toSeconds();
                    codeObj.setTime(codeObj.getInitialTime() - difference);
                    if (codeObj.getTime() < 0) {
                        codeRepository.deleteById(id);
                        deleted = true;
                    }
                }
            }
            if (!deleted) {
                codeRepository.save(codeObj);
            }
        }
        //return updated result
        return codeRepository.findById(id);
    }

    public List<CodeObj> findLatest() {
        return codeRepository.findAll().stream()
                .sorted(Comparator.comparing(CodeObj::getSavedTime).reversed())
                .filter(c -> !c.getHidden())
                .limit(10)
                .collect(Collectors.toList());
    }

    public void deleteById(String id) {
        codeRepository.deleteById(id);
    }
}

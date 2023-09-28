package platform.persistanceLayer;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import platform.businessLayer.CodeObj;

import java.util.List;

@Repository
public interface CodeRepository extends CrudRepository<CodeObj, String> {
    List<CodeObj> findAll();
}

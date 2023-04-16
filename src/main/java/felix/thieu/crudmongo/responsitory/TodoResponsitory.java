package felix.thieu.crudmongo.responsitory;

import felix.thieu.crudmongo.model.TodoDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.yaml.snakeyaml.events.Event;

import java.util.Optional;

@Repository
public interface TodoResponsitory extends MongoRepository<TodoDTO, String> {

    @Query("{'todo':?0}")
    Optional<TodoDTO> findByTodo(String todo);


}

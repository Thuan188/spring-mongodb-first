package felix.thieu.crudmongo.controller;

import felix.thieu.crudmongo.exception.TodoCollectionException;
import felix.thieu.crudmongo.model.TodoDTO;
import felix.thieu.crudmongo.responsitory.TodoResponsitory;
import felix.thieu.crudmongo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class TodoController {

    @Autowired
    private TodoResponsitory todoResponsitory;
    @Autowired
    private TodoService todoService;
    @GetMapping("/todos")
    public ResponseEntity<?> getAllTodos(){
        List<TodoDTO> todoDTOS=todoService.getAllTodos();
        return new ResponseEntity<>(todoDTOS.size()>0?HttpStatus.OK:HttpStatus.NOT_FOUND);
    }
    @PostMapping("/todos")
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO todo){
        try{
            todoService.createdTodo(todo);
            return new ResponseEntity<TodoDTO>(todo,HttpStatus.OK);
        }catch (ConstraintViolationException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.UNPROCESSABLE_ENTITY);
        }catch (TodoCollectionException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
        }
    }
    @GetMapping("/todos/{id}")
    public ResponseEntity<?> getSingleTodo(@PathVariable("id") String id){
        /*Optional<TodoDTO> todoOptional =todoResponsitory.findById(id);
        if (todoOptional.isPresent()){
            return new ResponseEntity<>(todoOptional.get(), HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Todo not found with id" +id,HttpStatus.NOT_FOUND);
        }*/
        try {
            return new ResponseEntity<>(todoService.getSingleTodo(id),HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/todos/{id}")
    public ResponseEntity<?> updateById(@PathVariable("id") String id, @RequestBody TodoDTO todo){
       /* Optional<TodoDTO> todoOptional =todoResponsitory.findById(id);
        if (todoOptional.isPresent()){
         TodoDTO todoToSave =   todoOptional.get();
         todoToSave.setComplete(todo.getComplete() != null?todo.getComplete():todoToSave.getComplete());
         todoToSave.setTodo(todo.getTodo()!=null?todo.getTodo():todoToSave.getTodo());
         todoToSave.setDescription(todo.getDescription()!=null?todo.getDescription():todoToSave.getDescription());
         todoToSave.setUpdated(new Date(System.currentTimeMillis()));
         todoResponsitory.save(todoToSave);
         return new ResponseEntity<>(todoToSave,HttpStatus.OK);
                    }else {
            return new ResponseEntity<>("No todos available @{id}"+id,HttpStatus.NOT_FOUND);
        }*/
        try {
            todoService.updateTodo(id, todo);
            return new ResponseEntity<>("Update Todo with id"+id,HttpStatus.OK);
        }catch (ConstraintViolationException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.UNPROCESSABLE_ENTITY);
        }catch (TodoCollectionException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/todos/{id}")
    public ResponseEntity<?> deletedById(@PathVariable("id") String id){
        try{
            todoService.deleteTodoById(id);
            return new ResponseEntity<>("Sucessfully deleted with id"+id,HttpStatus.OK);
        }catch (TodoCollectionException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }

    }
}

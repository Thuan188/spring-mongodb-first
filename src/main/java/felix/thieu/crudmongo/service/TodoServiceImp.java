package felix.thieu.crudmongo.service;

import felix.thieu.crudmongo.exception.TodoCollectionException;
import felix.thieu.crudmongo.model.TodoDTO;
import felix.thieu.crudmongo.responsitory.TodoResponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TodoServiceImp implements TodoService{
    @Autowired
    private TodoResponsitory todoResponsitory;
    @Override
    public void createdTodo(TodoDTO todo) throws ConstraintViolationException, TodoCollectionException {
        Optional<TodoDTO> todoDTOOptional=todoResponsitory.findByTodo(todo.getTodo());
        if (todoDTOOptional.isPresent()){
            throw new TodoCollectionException(TodoCollectionException.TodoAlreadyExists());

        }else {
            todo.setCreated(new Date(System.currentTimeMillis()));
            todoResponsitory.save(todo);
        }
    }

    @Override
    public List<TodoDTO> getAllTodos() {
        List<TodoDTO> todoDTOS= todoResponsitory.findAll();
    if(todoDTOS.size()>0){
        return todoDTOS;
    }else {
        return new ArrayList<TodoDTO>();
    }
    }
    public TodoDTO getSingleTodo(String id) throws TodoCollectionException{
        Optional<TodoDTO> optionalTodoDTO=todoResponsitory.findById(id);
        if(!optionalTodoDTO.isPresent()){
            throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));
        }
        else {
            return optionalTodoDTO.get();
        }
    }

    @Override
    public void updateTodo(String id, TodoDTO todoDTO) throws TodoCollectionException {
        Optional<TodoDTO> todoDTOOptional = todoResponsitory.findById(id);
    Optional<TodoDTO> todoWithSameName= todoResponsitory.findByTodo(todoDTO.getTodo());


        if(todoDTOOptional.isPresent()){

            if(todoWithSameName.isPresent()&& todoWithSameName.get().getId().equals(id)){
                throw new TodoCollectionException(TodoCollectionException.TodoAlreadyExists());

            }
      TodoDTO todoDTUpdate =  todoDTOOptional.get();

      todoDTUpdate.setTodo(todoDTO.getTodo());
      todoDTUpdate.setDescription(todoDTO.getDescription());
      todoDTUpdate.setComplete(todoDTO.getComplete());
      todoDTUpdate.setUpdated(new Date(System.currentTimeMillis()));
      todoResponsitory.save(todoDTUpdate);
    }else {
        throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));
    }

    }

    @Override
    public void deleteTodoById(String id) throws TodoCollectionException {
        Optional<TodoDTO> todoDTOOptional= todoResponsitory.findById(id);
    if(!todoDTOOptional.isPresent()){
        throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));
    }else {
        todoResponsitory.deleteById(id);
    }
    }

}

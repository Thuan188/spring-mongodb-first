package felix.thieu.crudmongo.service;

import felix.thieu.crudmongo.exception.TodoCollectionException;
import felix.thieu.crudmongo.model.TodoDTO;

import javax.validation.ConstraintViolationException;
import java.util.List;

public interface TodoService {
    public void createdTodo(TodoDTO todo) throws ConstraintViolationException, TodoCollectionException;

    public List<TodoDTO> getAllTodos();
    public TodoDTO getSingleTodo(String id) throws TodoCollectionException;

    public void updateTodo(String id,TodoDTO todoDTO) throws TodoCollectionException;
    public void deleteTodoById(String id) throws TodoCollectionException;
}

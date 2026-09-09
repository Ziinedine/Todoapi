package com.example.todo_api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/todos")
public class TodoController {


    Map<Long, Todo> map = new HashMap<Long, Todo>();
    Long idCounter = 1L;

    @PostMapping
    public Todo createTodo(@RequestBody Todo t1) {
        t1.setId(idCounter);
        map.put(idCounter, t1);
        idCounter++;

        System.out.println("todo!");
        return t1;
    }

    @GetMapping
    public List<Todo> getAllTodos() {
        List<Todo> list = new ArrayList<Todo>();
        for (Todo t : map.values()) {
            list.add(t);
        }
        return list;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable Long id) {
        Todo t = map.get(id);

        if (t == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(t, HttpStatus.OK);
    }

    @GetMapping("/search")
    public List<Todo> searchTodos(@RequestParam String title) {
        List<Todo> result = new ArrayList<Todo>();

        for (Todo t : map.values()) {
            if (t.getTitle().toLowerCase().contains(title.toLowerCase())) {
                result.add(t);
            }
        }
        return result;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable Long id, @RequestBody Todo t) {
        if (map.containsKey(id) == true) {
            t.setId(id);
            map.put(id, t);
            return new ResponseEntity<>(t, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        if (map.containsKey(id)) {
            map.remove(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

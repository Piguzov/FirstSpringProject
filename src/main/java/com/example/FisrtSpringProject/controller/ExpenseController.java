package com.example.FisrtSpringProject.controller;

import com.example.FisrtSpringProject.repository.Expense;
import com.example.FisrtSpringProject.repository.expenseCategory;
import com.example.FisrtSpringProject.service.ExpenseService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService){
        this.expenseService = expenseService;
    }

    @GetMapping
    public List<Expense> findAll(){
        return expenseService.findAll();
    }

    @GetMapping(path = "{id}")
    public Optional<Expense> getById(@PathVariable(name = "id") Long id){
        return expenseService.getById(id);
    }

    @PostMapping
    public Expense create(@RequestBody Expense expense){
        return expenseService.create(expense);
    }

    @PutMapping(path = "{id}")
    public void update(
            @PathVariable Long id,
            @RequestParam(required = false) Long amount,
            @RequestParam(required = false) LocalDate date,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) expenseCategory category
            ){
        expenseService.update(id,amount,date,description,category);
    }
    @DeleteMapping(path = "{id}")
    public void delete(@PathVariable Long id){
        expenseService.delete(id);
    }
}

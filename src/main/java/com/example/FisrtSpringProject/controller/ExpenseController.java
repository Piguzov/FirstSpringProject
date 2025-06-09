package com.example.FisrtSpringProject.controller;

import com.example.FisrtSpringProject.repository.Expense;
import com.example.FisrtSpringProject.repository.expenseCategory;
import com.example.FisrtSpringProject.service.ExpenseService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(path = "expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService){
        this.expenseService = expenseService;
    }
    //Вывести все расходы с фильтрацией по дате или без нее.
    @GetMapping
    public List<Expense> findAll(
            @RequestParam(required = false) LocalDate date1,
            @RequestParam(required = false) LocalDate date2
    ){
        return expenseService.findAll(date1,date2);
    }
    //Получить расход по id
    @GetMapping(path = "{id}")
    public Optional<Expense> getById(@PathVariable(name = "id") Long id){
        return expenseService.getById(id);
    }
    //Добавить расход
    @PostMapping
    public Expense create(@RequestBody Expense expense){
        return expenseService.create(expense);
    }
    //Изменить расход по id
    @PutMapping(path = "{id}")
    public void update(
            @PathVariable Long id,
            @RequestParam(required = false) Double amount,
            @RequestParam(required = false) LocalDate date,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) expenseCategory category
            ){
        expenseService.update(id,amount,date,description,category);
    }
    //Удалить расход
    @DeleteMapping(path = "{id}")
    public void delete(@PathVariable Long id){
        expenseService.delete(id);
    }
    //Вывести сумму расходов с фильтрацией по дате или без нее
    @GetMapping(path = "summary")
    public Double getSum(
            @RequestParam(required = false) LocalDate date1,
            @RequestParam(required = false) LocalDate date2
    ){
        return expenseService.getSum(date1,date2);
    }
}

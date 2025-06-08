package com.example.FisrtSpringProject.service;

import com.example.FisrtSpringProject.repository.Expense;
import com.example.FisrtSpringProject.repository.ExpenseRepository;
import com.example.FisrtSpringProject.repository.expenseCategory;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public List<Expense> findAll(){
        return expenseRepository.findAll();
    }

    public Optional<Expense> getById(Long id){
        return expenseRepository.findById(id);
    }

    @Transactional
    public  Expense create(Expense expense){
        return expenseRepository.save(expense);
    }

    @Transactional
    public void update(Long id, Long amount, LocalDate date, String description, expenseCategory category) {
        Optional<Expense> optionalExpense = expenseRepository.findById(id);
        if (optionalExpense.isEmpty()){
            throw new IllegalStateException("Расхода с таким id не существует");
        }
        Expense expense = optionalExpense.get();
        if(amount!=null){ expense.setAmount(amount);}
        if(date!=null){expense.setDate(date);}
        if(description!=null){expense.setDescription(description);}
        if(category!=null){expense.setCategory(category);}
    }

    @Transactional
    public void delete(Long id) {
        Optional<Expense> optionalExpense = expenseRepository.findById(id);
        if (optionalExpense.isEmpty()){
            throw new IllegalStateException("Расхода с таким id не существует");
        }
        expenseRepository.deleteById(id);
    }
}

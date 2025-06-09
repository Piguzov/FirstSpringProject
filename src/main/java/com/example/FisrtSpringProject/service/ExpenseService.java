package com.example.FisrtSpringProject.service;

import com.example.FisrtSpringProject.repository.Expense;
import com.example.FisrtSpringProject.repository.ExpenseRepository;
import com.example.FisrtSpringProject.repository.expenseCategory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.example.FisrtSpringProject.repository.ExpenseSpecs.isBetweenDates;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public List<Expense> findAll(LocalDate date1,LocalDate date2){
        return expenseRepository.findAll(isBetweenDates(date1,date2));
    }

    public Optional<Expense> getById(Long id){
        return expenseRepository.findById(id);
    }

    @Transactional
    public  Expense create(Expense expense){
        return expenseRepository.save(expense);
    }

    @Transactional
    public void update(Long id, Double amount, LocalDate date, String description, expenseCategory category) {
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
    @Autowired
    private EntityManager entityManager;
    public Double getSum(LocalDate date1,LocalDate date2) {
        CriteriaBuilder cb=entityManager.getCriteriaBuilder();
        CriteriaQuery<Double> cq = cb.createQuery(Double.class);
        Root<Expense> root = cq.from(Expense.class);

        Predicate predicate = isBetweenDates(date1,date2).toPredicate(root,cq,cb);
        //Используем спецификацию для фильтрации по дате и подсчитываем сумму колонки amount в выборке
        cq.select(cb.sum(root.get("amount"))).where(predicate);

        TypedQuery<Double> query = entityManager.createQuery(cq);
        Double res=query.getSingleResult();
        return res!=null ? res:0;
    }
}

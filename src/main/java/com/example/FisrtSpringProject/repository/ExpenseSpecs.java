package com.example.FisrtSpringProject.repository;

import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class ExpenseSpecs {
    //Спецификация, которая фильтрует строки по датам. date1 - нижняя граница, date2 - верхняя граница. При значениях null границы нет.
    public static Specification<Expense> isBetweenDates(LocalDate date1,LocalDate date2){
        return (root, query, criteriaBuilder) -> {
            if(date1==null & date2==null){
                return criteriaBuilder.conjunction();
            }
            if(date1==null) {
                return criteriaBuilder.lessThan(root.get("date"),date2);
            }
            if(date2==null){
                return criteriaBuilder.greaterThan(root.get("date"),date1);
            }
            return criteriaBuilder.between(root.get("date"),date1,date2);
        };
    }
}

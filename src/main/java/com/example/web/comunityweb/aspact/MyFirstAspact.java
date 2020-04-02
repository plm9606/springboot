package com.example.web.comunityweb.aspact;

import com.example.web.comunityweb.domain.Product;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MyFirstAspact {
    @Before("execution(* com.example.web.comunityweb.repository.ProductDao.findByProductName(String))")
    public void before(JoinPoint jp){
        System.out.println("Before :: 메서드가 호출되기 전에 나온다");
        Signature signature = jp.getSignature();
        System.out.println("-- 메서드 이름 "+ signature.getName());
        Object[] objects = jp.getArgs();
        System.out.println("-- 인수값 "  +objects[0]);
    }

    @After("execution(* com.example.web.comunityweb.repository.ProductDao.findByProductName(String))")
    public void after(){
        System.out.println("After :: 메서드가 호출된 후에 나온다");
    }

    @AfterReturning(value = "execution(* com.example.web.comunityweb.repository.ProductDao.findByProductName(String))", returning = "p")
    public void afterReturning(Product p){
        System.out.println("AfterReturning :: 메서드 호출이 예외를 보내지 않고 종료되었을때 나온다");
    }

    @Around("execution(* com.example.web.comunityweb.repository.ProductDao.findByProductName(String))")
    public Product around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("Around Before :: 메서드가 호출되기 전에 나온다");
        Product product = (Product)proceedingJoinPoint.proceed();
        System.out.println("Around After :: 메서드가 호출된 후에 나온다");
        return product;
    }

    @AfterThrowing(value = "execution(* com.example.web.comunityweb.repository.ProductDao.findByProductName(String))", throwing = "ex")
    public void afterThrowing(Throwable ex){
        System.out.println("throwing :: 예외가 생기면 나온다");
    }
}

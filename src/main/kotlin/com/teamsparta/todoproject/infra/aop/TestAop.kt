package com.teamsparta.todoproject.infra.aop

import org.aopalliance.intercept.Joinpoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component

//@Aspect
//@Component
class TestAop {

    @Around("execution(* com.teamsparta.todoproject.domain.post.service.PostService.getPostById(..))")
    fun thisIsAdvice(joinpoint: ProceedingJoinPoint) {
        println("AOP start")
        joinpoint.proceed()
        println("AOP end")
    }
}
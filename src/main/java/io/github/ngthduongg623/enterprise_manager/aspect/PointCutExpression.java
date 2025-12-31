package io.github.ngthduongg623.enterprise_manager.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class PointCutExpression {

    //declare PointCuts that match packages
    @Pointcut("execution(* com.mrurespect.employeeapp.controller.*.*(..))")
    public void controllerPackage(){}
    @Pointcut("execution(* com.mrurespect.employeeapp.dao.*.*(..))")
    public void daoPackage(){}
    @Pointcut("execution(* com.mrurespect.employeeapp.service.*.*(..))")
    public void servicePackage(){}

    @Pointcut("controllerPackage() || daoPackage() || servicePackage()")
    public void appFlow(){}

}
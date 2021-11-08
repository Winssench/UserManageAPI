package com.airFrance.offertest.advice;


import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Enumeration;
 
import javax.servlet.http.HttpServletRequest;
 
//import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
@Aspect
@Configuration
public class LoggingAspect {
	/*
	private static final Logger logger = LogManager.getLogger(LoggingAspect.class);
	
	
	@Around("within(@org.springframework.web.bind.annotation.RestController *) && within(com.airFrance.offertest..*)")
	public Object logAround(ProceedingJoinPoint joinpoint) throws Throwable {
		
		logger.debug("Request for {}.{}() with arguments[s]={}",joinpoint.getSignature().getDeclaringTypeName(), 
				joinpoint.getSignature().getName() , Arrays.toString(joinpoint.getArgs()));
		
		Instant start = Instant.now();
		
		Object returnValue = joinpoint.proceed();
		
		Instant finish = Instant.now();
				
		long timeElapsed = Duration.between(start, finish).toMillis();
		
		logger.debug("Response for {}.{} with Result ={}",joinpoint.getSignature().getDeclaringTypeName(), 
				joinpoint.getSignature().getName() ,returnValue);
		
		logger.info("Request for {}.{}() Execution Time={}",joinpoint.getSignature().getDeclaringTypeName(), 
				joinpoint.getSignature().getName() , new SimpleDateFormat("mm:ss:SSS").format(new Date(timeElapsed)));
		
		return returnValue;
	}
	*/
	Logger log = LogManager.getLogger(LoggingAspect.class);
	 
    @Pointcut("within(@org.springframework.stereotype.Controller *)")
    public void controller() {
    }
 
    @Pointcut("execution(* *.*(..))")
    protected void allMethod() {
    }
 
    @Pointcut("execution(public * *(..))")
    protected void loggingPublicOperation() {
    }
 
    @Pointcut("execution(* *.*(..))")
    protected void loggingAllOperation() {
    }
 
    @Pointcut("within(org.learn.log..*)")
    private void logAnyFunctionWithinResource() {
    }
 
    //before -> Any resource annotated with @Controller annotation 
    //and all method and function taking HttpServletRequest as first parameter
    @Before("controller() && allMethod() && args(..,request)")
    public void logBefore(JoinPoint joinPoint, HttpServletRequest request) {
 
        log.debug("Entering in Method :  " + joinPoint.getSignature().getName());
        log.debug("Class Name :  " + joinPoint.getSignature().getDeclaringTypeName());
        log.debug("Arguments :  " + Arrays.toString(joinPoint.getArgs()));
        log.debug("Target class : " + joinPoint.getTarget().getClass().getName());
 
        if (null != request) {
            log.debug("Start Header Section of request ");
            log.debug("Method Type : " + request.getMethod());
            Enumeration headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement().toString();
                String headerValue = request.getHeader(headerName);
                log.debug("Header Name: " + headerName + " Header Value : " + headerValue);
            }
            log.debug("Request Path info :" + request.getServletPath());
            log.debug("End Header Section of request ");
        }
    }
    //After -> All method within resource annotated with @Controller annotation 
    // and return a  value
    @AfterReturning(pointcut = "controller() && allMethod()", returning = "result")
    public void logAfter(JoinPoint joinPoint, Object result) {
        String returnValue = this.getValue(result);
        log.debug("Method Return value : " + returnValue);
    }
    //After -> Any method within resource annotated with @Controller annotation 
    // throws an exception ...Log it
    @AfterThrowing(pointcut = "controller() && allMethod()", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        log.error("An exception has been thrown in " + joinPoint.getSignature().getName() + " ()");
        log.error("Cause : " + exception.getCause());
    }
    //Around -> Any method within resource annotated with @Controller annotation 
    @Around("controller() && allMethod()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
         
        long start = System.currentTimeMillis();
        try {
            String className = joinPoint.getSignature().getDeclaringTypeName();
            String methodName = joinPoint.getSignature().getName();
            Object result = joinPoint.proceed();
            long elapsedTime = System.currentTimeMillis() - start;
            log.debug("Method " + className + "." + methodName + " ()" + " execution time : "
                    + elapsedTime + " ms");
         
            return result;
        } catch (IllegalArgumentException e) {
            log.error("Illegal argument " + Arrays.toString(joinPoint.getArgs()) + " in "
                    + joinPoint.getSignature().getName() + "()");
            throw e;
        }
    }
    private String getValue(Object result) {
        String returnValue = null;
        if (null != result) {
            if (result.toString().endsWith("@" + Integer.toHexString(result.hashCode()))) {
                returnValue = result.toString();
            } else {
                returnValue = result.toString();
            }
        }
        return returnValue;
    }
}

/*
 * this is responsible for all the logging
 */
package com.airFrance.offertest.advice;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;

import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * this is represents  the Logging interceptor for each method
 * @author chichaouiomar
 *
 */

@Aspect
@Component
public class LoggingAspect {

	private static final Logger logger = LogManager.getLogger(LoggingAspect.class);

	/**
	 * this indicate for the logging Interceptor where to look
	 */
	@Pointcut(value = "execution(* com.airFrance.offertest.*.*.*(..) )")
	public void myPointcut() {

	}

	/**
	 * Search in all the api layers and provide the loggs for the request and the response
	 * @param joinpoint
	 * @return Object
	 * @throws Throwable
	 */
	@Around("myPointcut()")
	public Object logAround(ProceedingJoinPoint joinpoint) throws Throwable {

		logger.debug("Request for {}.{}() with arguments[s]={}", joinpoint.getSignature().getDeclaringTypeName(),
				joinpoint.getSignature().getName(), Arrays.toString(joinpoint.getArgs()));

		Instant start = Instant.now();

		Object returnValue = joinpoint.proceed();

		Instant finish = Instant.now();

		long timeElapsed = Duration.between(start, finish).toMillis();

		logger.debug("Response for {}.{} with Result ={}", joinpoint.getSignature().getDeclaringTypeName(),
				joinpoint.getSignature().getName(), returnValue);

		logger.info("Request for {}.{}() Execution Time={}", joinpoint.getSignature().getDeclaringTypeName(),
				joinpoint.getSignature().getName(), new SimpleDateFormat("mm:ss:SSS").format(new Date(timeElapsed)));

		return returnValue;
	}

}

package com.xu.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.google.common.util.concurrent.RateLimiter;

@Component
@Aspect
public class LimitAspect {
	private RateLimiter rateLimiter = RateLimiter.create(5.0);

	@Pointcut("@annotation(com.xu.aop.ServiceLimit)")
	public void ServiceAspect() {
	}

	@Around("ServiceAspect()")
	public Object around(ProceedingJoinPoint joinPoint) {
		Object object = null;
		try {
			if (rateLimiter.tryAcquire()) {
				object = joinPoint.proceed();
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return object;
	}
}

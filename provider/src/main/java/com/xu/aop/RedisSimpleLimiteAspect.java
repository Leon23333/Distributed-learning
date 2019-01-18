package com.xu.aop;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.xu.utils.RedisSimpleRateLimiter;

@Component
@Aspect
public class RedisSimpleLimiteAspect {
	@Autowired
	private RedisSimpleRateLimiter simpleRateLimiter;

	@Pointcut("@annotation(com.xu.aop.RedisSimpleLimit)")
	public void ServiceAspect() {

	}

	@Around(value = "ServiceAspect()")
	public Object around(ProceedingJoinPoint joinPoint) {
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		RedisSimpleLimit limitAnnotation = methodSignature.getMethod().getAnnotation(RedisSimpleLimit.class);
		String value = Thread.currentThread().getName();
		Object object = null;
		try {
			boolean isAllowed = simpleRateLimiter.isActionAllowed(limitAnnotation.key(), value, limitAnnotation.priod(),
					limitAnnotation.maxCount());
			if (isAllowed) {
				object = joinPoint.proceed();
			}else {
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return object;
	}
}

package com.MatheusJFA.Digibank.infrastructure.security;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class SecurityAuditAspect {

    @Before("@annotation(org.springframework.security.access.prepost.PreAuthorize)")
    public void logSecurityCheck(JoinPoint joinPoint) {
        String username = getCurrentUsername();
        String methodName = joinPoint.getSignature().getName();
        String args = Arrays.toString(joinPoint.getArgs());

        log.info("SECURITY_CHECK - Usuário: {} está tentando acessar o método: {} com os argumentos: {}", username, methodName, args);
    }

    @AfterReturning("@annotation(org.springframework.security.access.prepost.PreAuthorize)")
    public void logSecuritySuccess(JoinPoint joinPoint) {
        String username = getCurrentUsername();
        String methodName = joinPoint.getSignature().getName();

        log.info("SECURITY_SUCCESS - Usuário: {} acessou com sucesso o método: {}", username, methodName);
    }

    @AfterThrowing(
            pointcut = "execution(* com.MatheusJFA.Digibank.application.services..*(..))",
            throwing = "exception"
    )
    public void logSecurityFailure(JoinPoint joinPoint, Throwable exception) {
        String username = getCurrentUsername();
        String methodName = joinPoint.getSignature().getName();
        String args = Arrays.toString(joinPoint.getArgs());

        log.error("SECURITY_FAILURE - Usuário: {} tentou acessar o método: {} com os argumentos: {} e falhou com a exceção: {}", username, methodName, args, exception.getMessage());
    }

    private String getCurrentUsername() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null ? authentication.getName() : "ANONYMOUS";
    }
}

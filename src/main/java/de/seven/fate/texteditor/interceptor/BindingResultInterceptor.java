package de.seven.fate.texteditor.interceptor;

import de.seven.fate.texteditor.converter.ObjectError2ErrorMessageConverter;
import de.seven.fate.texteditor.vo.ErrorMessage;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.List;

/**
 * Interceptor for all Components under package: com.e2open.datahub.core.rest
 */
@Aspect
@Component
public class BindingResultInterceptor {

    private final ObjectError2ErrorMessageConverter objectError2ErrorMessageConverter;

    public BindingResultInterceptor(ObjectError2ErrorMessageConverter objectError2ErrorMessageConverter) {
        this.objectError2ErrorMessageConverter = objectError2ErrorMessageConverter;
    }


    @Pointcut("within(de.seven.fate.texteditor.resource..*)")
    public void resourcePointcut() {
        //Point cut
    }

    @Around("resourcePointcut()")
    public Object intercept(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        Object[] args = proceedingJoinPoint.getArgs();

        BindingResult bindingResult = findBindingResult(args);

        if (bindingResult != null && bindingResult.hasErrors()) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorMessages(bindingResult));
        }

        return proceedingJoinPoint.proceed();
    }

    private List<ErrorMessage> createErrorMessages(BindingResult bindingResult) {
        assert bindingResult != null;

        return objectError2ErrorMessageConverter.convertToList(bindingResult.getAllErrors());
    }

    private BindingResult findBindingResult(Object[] args) {
        assert args != null;

        for (Object arg : args) {

            if (arg instanceof BindingResult) {

                return (BindingResult) arg;
            }
        }

        return null;
    }

}

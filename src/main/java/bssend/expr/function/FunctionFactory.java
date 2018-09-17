package bssend.expr.function;

import bssend.expr.annotation.Function;
import bssend.expr.exception.ExprCompileException;
import lombok.NonNull;
import lombok.var;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FunctionFactory {

    private static Reflections REFRECTIONS
            = new Reflections("bssend.expr.function", new MethodAnnotationsScanner());

    public static Method create(
            @NonNull final String name,
            @NonNull final List<Class<?>> argTypes) {

        Set<Method> methods =
                REFRECTIONS.getMethodsAnnotatedWith(Function.class);

        var matchedMethods = methods.stream()
                .filter(m -> m.getAnnotation(Function.class).value().equals(name))
                .collect(Collectors.toList());

        if (matchedMethods.size() == 0)
            throw new ExprCompileException("function not found (" + name + ")");

        matchedMethods = methods.stream()
                .filter(m -> m.getParameterCount() == argTypes.size())
                .collect(Collectors.toList());

        if (matchedMethods.size() == 0)
            throw new ExprCompileException("function parameter count not matched (" + name + ")");

        matchedMethods = methods.stream()
                .filter(m -> Arrays.equals(m.getParameterTypes(), argTypes.toArray()))
                .collect(Collectors.toList());

        if (matchedMethods.size() == 0)
            throw new ExprCompileException("function parameter type not matched (" + name + ")");

        return matchedMethods.stream()
            .findFirst().get();

//        return methods.stream()
//                .filter(m -> m.getAnnotation(Function.class).value().equals(name))
//                .filter(m -> m.getParameterCount() == argTypes.size())
//                .filter(m -> Arrays.equals(m.getParameterTypes(), argTypes.toArray()))
//                .findFirst()
//                .orElseThrow(() -> new ClassNotFoundException())
//                ;

//        Set<Class<?>> classes =
//                REFRECTIONS.getTypesAnnotatedWith(Function.class);
//
//        var clazz = classes.stream()
//                .filter(c -> c.getAnnotation(Function.class).value().equals(name))
//                .findFirst()
//                .orElseThrow(() -> new ClassNotFoundException())
//        ;
//
//        return (IFunction)clazz.newInstance();
    }
}

package com.javapro.lesson22.model;

import com.javapro.lesson22.exception.MyException;
import com.javapro.lesson22.annotation.AfterSuite;
import com.javapro.lesson22.annotation.BeforeSuite;
import com.javapro.lesson22.annotation.Test;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


/**
 * @author Andrii Andriutsa on 09.11.2022
 */
public class TestRunner {

  public static void start(Class<?> clazz) {
    if (clazz != null) {
      Object testClass;
      try {
        testClass = clazz.getDeclaredConstructor().newInstance();
      } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
               NoSuchMethodException e) {
        throw new RuntimeException(e);
      }
      List<Method> before = getBefore(clazz);
      List<Method> test = getTest(clazz);
      List<Method> after = getAfter(clazz);
      List<Method> allMethodsForStart = getMethodsForStart(before, test, after);

      for (Method method : allMethodsForStart) {

        try {
          method.invoke(testClass);
        } catch (IllegalAccessException | InvocationTargetException e) {
          throw new RuntimeException(e);
        }
      }
    }
  }

  private static List<Method> getMethodsForStart(List<Method> before, List<Method> test,
      List<Method> after) {
    List<Method> allMethodsForStart = new ArrayList<>();
    allMethodsForStart.addAll(before);
    allMethodsForStart.addAll(test);
    allMethodsForStart.addAll(after);
    return allMethodsForStart;
  }

  private static List<Method> getAfter(Class<?> clazz) {
    List<Method> after = Arrays.stream(clazz.getMethods())
        .filter(method -> method.isAnnotationPresent(AfterSuite.class)).toList();
    if (after.size() > 1) {
      throw new MyException("@AfterSuite  annotated method within the same test-class scope" +
          "may be frequent in a single instance");
    }
    return after;
  }

  private static List<Method> getTest(Class<?> clazz) {
    return Arrays.stream(clazz.getMethods())
        .filter(method -> method.isAnnotationPresent(Test.class))
        .sorted(Comparator.comparing(method -> method.getAnnotation(Test.class).value()))
        .toList();
  }

  private static List<Method> getBefore(Class<?> clazz) {

    List<Method> before = Arrays.stream(clazz.getMethods())
        .filter(method -> method.isAnnotationPresent(BeforeSuite.class)).toList();
    if (before.size() > 1) {
      throw new MyException("@BeforeSuite annotated method within the same test-class scope" +
          "may be frequent in a single instance");
    }
    return before;
  }
}




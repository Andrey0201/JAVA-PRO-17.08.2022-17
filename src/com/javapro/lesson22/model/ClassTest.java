package com.javapro.lesson22.model;


import com.javapro.lesson22.annotation.AfterSuite;
import com.javapro.lesson22.annotation.BeforeSuite;
import com.javapro.lesson22.annotation.Test;


/**
 * @author Andrii Andriutsa on 09.11.2022
 */

public class ClassTest {

  @BeforeSuite
  public void before() {
    System.out.println("@BeforeSuite");
  }

  @Test(1)
  public void test1() {
    System.out.println("Test1");
  }


  @Test(2)
  public void test2() {
    System.out.println("Test2");
  }

  @Test(5)
  public void test5() {
    System.out.println("Test5");
  }

  @Test(3)
  public void test3() {
    System.out.println("Test3");
  }

  @Test(4)
  public void test4() {
    System.out.println("Test4");

  }


  @AfterSuite
  public void after() {
    System.out.println("@AfterSuite");
  }

}

package org.millions.idea.ocr.web.common.utility.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class PropertyUtilTest {
    @Test
    public void testParentPropertyClone() throws InterruptedException {
        A a = new A();
        a.setName("wade");
        a.setSex(1);
        B b = new B();
        b.setAge(1);
        assert b.getName() == null;
        PropertyUtil.clone(a,b);
        assert b.getName() != null;
        assert b.getSex() != null;
    }
}


class A{
    private String name;
private Integer sex;

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "A{" +
                "name='" + name + '\'' +
                '}';
    }
}

class B extends A{
    private  Integer age;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "B{" +
                "name=" + getName() +
                "," +
                "sex=" + getSex() +
                "," +
                "age=" + age +
                '}';
    }
}
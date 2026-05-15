package com.example.test;

public class Animal {
    public void eat(){
        System.out.println("animal eating..."); // 修正拼写错误
    }
}

class Bird extends Animal {
    @Override // 增加@Override注解，显式标记方法重写（规范写法）
    public void eat() {
        System.out.println("bird eating..."); // 修正拼写错误
    }

    public void fly() {
        System.out.println("bird flying...");
    }
}
    class Main{
        public static void main(String[] args) {
            Animal b = new Bird(); // 向上转型，多态的核心
            b.eat(); // 多态调用：执行子类Bird的eat()方法

            // 修复fly()调用：向下转型，将父类引用转回子类类型
            if (b instanceof Bird) { // 类型安全校验，避免转型异常
                Bird bird = (Bird) b;
                bird.fly();
            }
        }
}

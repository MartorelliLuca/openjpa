package org.apache.openjpa.util.objects;

import java.util.Random;

public class UnproxyCat {
    private final Random random = new Random(System.currentTimeMillis());
    private String name;
    private long age;

    public UnproxyCat(String name, long age) {
        this.name = name;
        this.age = age;
    }

    public void miao() {
        if(random.nextInt()%2 == 0){
            System.out.println("Hi, my name is " + name + " and I'm " + age + " years old.\n");
        }else{
            System.out.println("I'm a sleek cat with a love for quiet corners, but when a dog" +
                    " enters the room, my hiss tells the story!\n");
        }
    }
}

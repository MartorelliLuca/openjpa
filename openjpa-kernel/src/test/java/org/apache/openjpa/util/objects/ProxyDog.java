package org.apache.openjpa.util.objects;

import java.util.Random;

public class ProxyDog {
    private final Random random = new Random(System.currentTimeMillis());
    private String name;
    private int age;
    public ProxyDog() {
        if(random.nextInt()%2 == 0){
            this.name = "Luna";
            this.age = 3;
        }else{
            this.name = "Leone";
            this.age = 6;
        }
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public void bau(){
        System.out.println("Hello, this is " + name + " and I'm " + age + " years old...");
        if(random.nextInt()%2 == 0){
            System.out.println("I'm a dog who loves everyone, but when it comes to cats, let's" +
                    " just say we're better off living in different worlds! \n");
        }else{
            System.out.println("I'm a happy pup with a wagging tail, but when a cat crosses my" +
                    " path, my bark says it all!\n");
        }
    }
}

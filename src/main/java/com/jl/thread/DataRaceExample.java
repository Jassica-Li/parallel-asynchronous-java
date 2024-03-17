package com.jl.thread;


public class DataRaceExample {

    public static void main(String[] args) {
        SharedClass sharedClass = new SharedClass();
        int times = 1000;
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < times; i++) {
                sharedClass.increment();
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < times; i++) {
                sharedClass.detectDataRace();
            }
        });


        thread1.start();
        thread2.start();
    }

    public static class SharedClass {

        private  int x = 0;
        private  int y = 0;

        public void increment() {
            x++;
            y++;
        }

        public void detectDataRace() {
            if (y > x) {
                System.out.println("y > x and a data race is detected");
            }
        }
    }
}



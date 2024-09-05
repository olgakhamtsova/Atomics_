package org.example;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static AtomicInteger atomicInt1 = new AtomicInteger(0);
    public static AtomicInteger atomicInt2 = new AtomicInteger(0);
    public static AtomicInteger atomicInt3 = new AtomicInteger(0);

    public static void main(String[] args) {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }
        ExecutorService executorService1 = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        Runnable task1 = () -> {
            for (String text : texts) {
                if (isPalindrome(text)) {
                    if (text.length() == 3) {
                        atomicInt1.incrementAndGet();
                    } else if (text.length() == 4) {
                        atomicInt2.incrementAndGet();
                    } else if (text.length() == 5) {
                        atomicInt3.incrementAndGet();
                    }
                }
            }
        };

        ExecutorService executorService2 = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        Runnable task2 = () -> {
            for (String text : texts) {
                if (isSameLetters(text)) {
                    if (text.length() == 3) {
                        atomicInt1.incrementAndGet();
                    } else if (text.length() == 4) {
                        atomicInt2.incrementAndGet();
                    } else if (text.length() == 5) {
                        atomicInt3.incrementAndGet();
                    }
                }
            }
        };

        ExecutorService executorService3 = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        Runnable task3 = () -> {
            for (String text : texts) {
                if (isAlphabetic(text)) {
                    if (text.length() == 3) {
                        atomicInt1.incrementAndGet();
                    } else if (text.length() == 4) {
                        atomicInt2.incrementAndGet();
                    } else if (text.length() == 5) {
                        atomicInt3.incrementAndGet();
                    }
                }
            }
        };
        executorService1.submit(task1);
        executorService2.submit(task2);
        executorService3.submit(task3);
        executorService1.shutdown();
        executorService2.shutdown();
        executorService3.shutdown();

        System.out.println("Красивых слов с длиной 3: " + atomicInt1.get() + " шт.");
        System.out.println("Красивых слов с длиной 4: " + atomicInt2.get() + " шт.");
        System.out.println("Красивых слов с длиной 5: " + atomicInt3.get() + " шт.");

    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static boolean isPalindrome(String str) {
        String rev = "";
        boolean ans = false;
        for (int i = str.length() - 1; i >= 0; i--) {
            rev = rev + str.charAt(i);
        }
        if (str.equals(rev)) {
            ans = true;
        }
        return ans;
    }

    public static boolean isSameLetters(String str) {
        boolean ans = false;
        String s = "";

        CharacterIterator it = new StringCharacterIterator(str);
        while (it.current() != CharacterIterator.DONE) {
            if (s.isEmpty()) {
                s = String.valueOf(it.current());
            } else {
                if (s.equals(String.valueOf(it.current()))) {
                    ans = true;
                    s = String.valueOf(it.current());
                } else ans = false;
            }
            it.next();
        }
        return ans;
    }

    public static boolean isAlphabetic(String str) {
        boolean ans = false;
        String sorted = str.chars().sorted().collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
        if (sorted.equals(str)) {
            ans = true;
        }
        return ans;
    }
}
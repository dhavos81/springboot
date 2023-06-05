package com.testing.lambda;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LambdaTest {

    @Test
    public void givenOptional_whenMapWorks_thenCorrect() {
        List<String> companyNames = Arrays.asList(
                "paypal", "oracle", "", "microsoft", "", "apple");
        Optional<List<String>> listOptional = Optional.of(companyNames);

        int size = listOptional
                .map(List::size)
                .orElseGet(()->0);
        assertEquals(6, size);
    }

    @Test
    public void givenOptional_whenMapDoasntWork_thenCorrect() {
        List<String> companyNames = null;
        Optional<List<String>> listOptional = Optional.ofNullable(companyNames);

        int size = listOptional
                .map(List::size)
                .orElseGet(()-> 0);

        int size2 = listOptional
                .map((list)-> list.size())
                .orElseGet(()-> 0);
        assertEquals(0, size);
        assertEquals(0, size2);
    }

    @Test
    public void testLambdas() {
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        numbers.add(5);
        numbers.add(9);
        numbers.add(8);
        numbers.add(1);
        numbers.forEach((n) -> {
            System.out.println(n);
        });
    }

    @Test
    public void testStreamsWithLambdas() {
        List<String> places = new ArrayList<>();
        // preparing our data
        places.add("Nepal, Kathmandu");
        places.add("Nepal, Pokhara");
        places.add("India, Delhi");
        places.add("USA, New York");
        places.add("Africa, Nigeria");



        System.out.println("Places from Nepal:");

        // Filter places from Nepal
        places.stream()
                .filter((p) -> p.startsWith("Nepal"))
                .map((p) -> p.toUpperCase())
                .sorted()
                .forEach((p) -> System.out.println(p));
    }
}

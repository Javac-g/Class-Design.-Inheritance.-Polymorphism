package task2;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MyUtils {
    public Map<String, Double> averageRating(List<Coffee> coffees) {

        return coffees.stream()
                .collect(Collectors.groupingBy(Coffee::getName, Collectors.averagingDouble(Coffee::getRating)));
    }

    public static void main(String... args){
        MyUtils here = new MyUtils();
    }
}

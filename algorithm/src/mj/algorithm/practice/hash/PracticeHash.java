package mj.algorithm.practice.hash;

import java.util.*;

public class PracticeHash {

    public static String hashOne(String[] participant, String[] completion){
        HashMap<String, Integer> map = new HashMap<>();

        Arrays.stream(participant).forEach(
                s -> {
                    if(map.containsKey(s)){
                        map.put(s, map.get(s)+1);
                    } else {
                        map.put(s, 1);
                    }
                }
        );

        Arrays.stream(completion).forEach(
                s -> {
                    if(map.containsKey(s)){
                        if(map.get(s) > 1){
                            map.put(s, map.get(s)-1);
                        } else {
                            map.remove(s);
                        }
                    }
                }
        );

        return map.keySet().iterator().next();
    }

    public static boolean hashTwo(String[] phone_book){
        HashMap<String, Integer> map = new HashMap<>();
        Arrays.stream(phone_book).forEach(s -> map.put(s, s.length()));
        for(int i = 0; i < phone_book.length; i++){
            for(int j = 1; j < phone_book[i].length(); j++){
                if(map.containsKey(phone_book[i].substring(0, j))){
                    return false;
                }
            }
        }

        return true;
    }

    public static int hashThree(String[][] clothes){
        int answer = clothes.length;

        HashMap<String, Integer> map = new HashMap<>();
        for (int i = 0; i < clothes.length; i++){
            if (map.containsKey(clothes[i][1])){
                map.put(clothes[i][1], map.get(clothes[i][1]) + 1);
            } else {
                map.put(clothes[i][1], 1);
            }
        }

        Iterator<Map.Entry<String, Integer>> iter = map.entrySet().iterator();
        int sum = 1;

        while (iter.hasNext()){
            sum = sum * (iter.next().getValue() + 1);
        }

        return sum-1;
    }

    // HashMap의 작동방식
    public static void hashTest(String caseText){
        switch (caseText){
            case "one" : {
                String[] p = {"leo", "kiki", "eden"};
                String c = "mj";

                HashMap<String, Integer> map = new HashMap<>();

                for(int i = 0; i < p.length; i++){
                    map.put(p[i], i);
                }

                // 없는 값을 찾으면 null
                System.out.println(map.get(c));
            }
            case "two" : {
                String[] p = {"leo", "kiki", "eden", "eden"};
                String c = "eden";

                HashMap<String, Integer> map = new HashMap<>();

                for(int i = 0; i < p.length; i++){
                    map.put(p[i], i);
                }

                // 중복된 키의 입력은 기존의 값을 대체
                System.out.println(map.get(c));
            }
            case "three" : {
                String[][] p = {{"yellowhat", "headgear"}, {"bluesunglasses", "eyewear"}, {"green_turban", "headgear"}};

                for (int i = 0; i < p.length; i++){
                    System.out.println(p[i][1]);
                }
            }
        }
    }
}

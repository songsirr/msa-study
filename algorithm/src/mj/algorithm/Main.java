package mj.algorithm;

import mj.algorithm.practice.hash.PracticeHash;

public class Main {

    public static void main(String[] args) {
//        String[] p = {"mislav", "stanko", "mislav", "ana"};
//        String[] c = {"stanko", "ana", "mislav"};
//        System.out.println(PracticeHash.hashOne(p, c));

//        String[] phone_book = {"123", "01234"};
//        System.out.println(PracticeHash.hashTwo(phone_book));
        String[][] p = {{"yellowhat", "headgear"}, {"bluesunglasses", "eyewear"}, {"green_turban", "headgear"}};
        PracticeHash.hashThree(p);
    }
}

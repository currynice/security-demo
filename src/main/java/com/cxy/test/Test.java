package com.cxy.test;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

public class Test {



    public static void main(String args[]) throws JsonProcessingException {
        Three t1 = new Three();
        t1.setId("31");t1.setName("三:1");

        Three t2 = new Three();
        t2.setId("32");t2.setName("三:2");

        Three t3 = new Three();
        t3.setId("33");t3.setName("三:3");

//        List<Three> threes = Arrays.asList(t1,t2,t3);

        Two b1 = new Two();
        b1.setId("21");b1.setName("二:1");
        List<Three> th1 = new ArrayList<>();th1.add(t1);
        b1.setThrees(th1);
        Two b2 = new Two();
        b2.setId("22");b2.setName("二:2");
        List<Three> th2 = new ArrayList<>();th2.add(t2);
        b2.setThrees(th2);
        Two b3 = new Two();
        b3.setId("23");b3.setName("二:3");
        List<Three> th3 = new ArrayList<>();th3.add(t3);
        b3.setThrees(th3);
//        List<Two> twos = Arrays.asList(b1,b2,b3);


        One a1 = new One();
        a1.setId("11");a1.setName("一:1");
        List<Two> tw1 = new ArrayList<>();tw1.add(b1);
        a1.setTwos(tw1);
        One a2 = new One();
        a2.setId("12");a2.setName("一:2");
        List<Two> tw2 = new ArrayList<>();tw2.add(b2);
        a2.setTwos(tw2);
        One a3 = new One();
        a3.setId("13");a3.setName("一:3");
        List<Two> tw3 = new ArrayList<>();tw3.add(b3);
        a3.setTwos(tw3);

        List<One> ones = new ArrayList<>();ones.add(a1);ones.add(a2);ones.add(a3);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writerWithDefaultPrettyPrinter();
//        System.out.println(objectMapper.writeValueAsString(ones));
        List<String> ids =new ArrayList<>();
        ids.add("31");ids.add("32");ids.add("21");ids.add("22");ids.add("12");

        System.out.println(objectMapper.writeValueAsString(filterThree(ones,ids)));
        System.out.println(objectMapper.writeValueAsString(filterTwo(filterThree(ones,ids),ids)));
        System.out.println(objectMapper.writeValueAsString(filterOne(filterTwo(filterThree(ones,ids),ids),ids)));
    }

    private static List<One> filterThree(List<One> list, List<String> ids){
        Iterator<One> iterator = list.iterator();
        while(iterator.hasNext()){
            One one = iterator.next();
            Iterator<Two> iterator2 = one.getTwos().iterator();
            while(iterator2.hasNext()){
                Two two = iterator2.next();
                Iterator<Three> iterator3 = two.getThrees().iterator();
                while(iterator3.hasNext()){
                    Three three = iterator3.next();
                    if(!ids.contains(three.getId())) {
                        iterator3.remove();
                     }
                }
            }
        }
        return list;
    }


    private static List<One> filterTwo(List<One> list, List<String> id2s){
        Iterator<One> iterator = list.iterator();
        while(iterator.hasNext()){
            One one = iterator.next();
            Iterator<Two> iterator2 = one.getTwos().iterator();
            while(iterator2.hasNext()){
                Two two = iterator2.next();
                if(!id2s.contains(two.getId())) {
                    iterator2.remove();
                }
            }
        }
        return list;
    }

    private static List<One> filterOne(List<One> list, List<String> ids){
        Iterator<One> iterator = list.iterator();
        while(iterator.hasNext()){
            One one = iterator.next();
                if(!ids.contains(one.getId())) {
                    iterator.remove();
                }

        }
        return list;
    }
}

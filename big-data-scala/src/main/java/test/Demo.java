package test;

import java.util.ArrayList;

public class Demo {

    public static void main(String[] args) {


        // List<Product> ....
        // 从list中过滤出商品价格大于10.98
        // List


        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);


        MyList myList = new MyList(list);

        ArrayList<Integer> rlist = myList.map(new IOperate() {
            @Override
            public Integer caoZuo(Integer ele) {
                return ele * 10;
            }
        });

        for (Integer r : rlist) {
            System.out.println("r = " + r);
        }
    }
}

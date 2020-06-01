package test;

import java.util.ArrayList;

public class MyList {

    private ArrayList<Integer> list;
    private ArrayList<Integer> resultList;


    public MyList(ArrayList<Integer> list) {
        this.list = list;
    }

    public ArrayList<Integer> map(IOperate op) {

        resultList = new ArrayList<>();

        for (Integer l : list) {

            resultList.add(op.caoZuo(l));
        }

        return resultList;
    }
}

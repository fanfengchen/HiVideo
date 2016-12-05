package com.ebeijia.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by YPJ on 2016/8/1.
 */
public class LotteryUtil {
    public static int lottery(List<Double> orignalRates) {
        if (orignalRates == null || orignalRates.isEmpty()) {
            return -1;
        }

        int size = orignalRates.size();

        double sumRate = 0d;
        for (double rate : orignalRates) {
            sumRate += rate;
        }

        List<Double> sortOrignalRates = new ArrayList<Double>(size);
        Double tempSumRate = 0d;
        for (double rate : orignalRates) {
            tempSumRate += rate;
            sortOrignalRates.add(tempSumRate / sumRate);
        }

        double nextDouble = Math.random();
        sortOrignalRates.add(nextDouble);
        Collections.sort(sortOrignalRates);

        return sortOrignalRates.indexOf(nextDouble);
    }
}

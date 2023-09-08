package com.adp.coindispenser.config;

import java.util.ArrayList;
import java.util.List;

public class ApplicationConstants {

    public static final String versionHeader = "X-Version";

    public static List<Integer> allowedBills = new ArrayList<>(List.of(1,2,5,10,20,50,100));

}

package gka;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther ly
 * @Date 2019/4/2
 * @Describe
 */
public class aaa {
    public static void main(String[] args) {
        formatZc("1,2,3,4,5,6,7,8,10,11,12");
        System.out.println(formatZc("2,4,6,8"));
        System.out.println(formatZc("1,2,4,6,8"));
        System.out.println(formatZc("1,3,4,6,8"));
        System.out.println(formatZc("1,2,3,4"));
        formatZc("1,2,3,4,6,7,8,9,11,13,15,17,19,21,22,23");
        formatZc("1,3,4,6,7,9,10,11,12,13,15,17,18,19");
        formatZc("1,2,3,5,6,7,8,10,11,12");
        formatZc("1,2,3,5");
        formatZc("1,4,7,8,9,11,15,18,20,22,25,29,31,33,34,35");
        formatZc("1,4,7,8,9,11,15,18,20,22,25,29,31,33,34,35,36,38,40,42");
        formatZc("1,4,7,8,9,11,15,18,20,22,25,29,31,33,34,35,36,38,39,41,42,43");
    }

    public static String formatZc(String zc) {
        StringBuffer zcBuffer = new StringBuffer();
        String[] zcs = zc.split(",");
        int curr = 0;
        int zcsLen = zcs.length;
        if (zcsLen > 2) {
            int d = Integer.parseInt(zcs[1]) - Integer.parseInt(zcs[0]);
            for (int i = 2; i < zcsLen; i++) {
                if ((Integer.parseInt(zcs[i]) != Integer.parseInt(zcs[i - 1]) + d) || (d != 1 && d != 2)) {
                    if (d == 1) {
                        zcBuffer.append(zcs[curr] + "-" + zcs[i - 1] + ",");
                    } else if (d == 2) {
                        if (Integer.parseInt(zcs[i - 1]) % 2 == 0) {
                            zcBuffer.append(zcs[curr] + "-" + zcs[i - 1] + "(双),");
                        } else {
                            zcBuffer.append(zcs[curr] + "-" + zcs[i - 1] + "(单),");
                        }
                    } else {
                        if (Integer.parseInt(zcs[curr]) + 1 == Integer.parseInt(zcs[curr + 1])) {
                            zcBuffer.append(zcs[curr] + "-" + zcs[curr + 1]);
                        } else if (Integer.parseInt(zcs[curr]) + 2 == Integer.parseInt(zcs[curr + 1])) {
                            if (Integer.parseInt(zcs[curr]) % 2 == 0) {
                                zcBuffer.append(zcs[curr] + "-" + zcs[curr + 1] + "(双)");
                            } else {
                                zcBuffer.append(zcs[curr] + "-" + zcs[curr + 1] + "(单)");
                            }
                        } else {
                            if ((Integer.parseInt(zcs[curr + 1]) + 1 == Integer.parseInt(zcs[curr + 2])) || (Integer.parseInt(zcs[curr + 1]) + 2 == Integer.parseInt(zcs[curr + 2]))) {
                                zcBuffer.append(zcs[curr] + ",");
                                i--;
                            } else {
                                zcBuffer.append(zcs[curr] + "," + zcs[curr + 1] + ",");
                            }
                        }
                    }
                    curr = i;
                    if (zcsLen - i < 3) {
                        if (zcsLen - i == 2) {
                            if (Integer.parseInt(zcs[curr]) + 1 == Integer.parseInt(zcs[zcsLen - 1])) {
                                zcBuffer.append(zcs[curr] + "-" + zcs[zcsLen - 1]);
                            } else if (Integer.parseInt(zcs[curr]) + 2 == Integer.parseInt(zcs[zcsLen - 1])) {
                                if (Integer.parseInt(zcs[curr]) % 2 == 0) {
                                    zcBuffer.append(zcs[curr] + "-" + zcs[zcsLen - 1] + "(双)");
                                } else {
                                    zcBuffer.append(zcs[curr] + "-" + zcs[zcsLen - 1] + "(单)");
                                }
                            } else {
                                zcBuffer.append(zcs[curr] + "," + zcs[zcsLen - 1]);
                            }

                        } else if (zcsLen == i + 1) {
                            zcBuffer.append(zcs[zcsLen - 1]);
                        }
                        break;
                    }
                    d = Integer.parseInt(zcs[i + 1]) - Integer.parseInt(zcs[i]);
                    i++;
                } else {
                    if (i == zcsLen - 1) {
                        if (Integer.parseInt(zcs[curr]) + 1 == Integer.parseInt(zcs[curr + 1])) {
                            zcBuffer.append(zcs[curr] + "-" + zcs[zcsLen - 1]);
                        } else if (Integer.parseInt(zcs[curr]) + 2 == Integer.parseInt(zcs[curr + 1])) {
                            if (Integer.parseInt(zcs[curr]) % 2 == 0) {
                                zcBuffer.append(zcs[curr] + "-" + zcs[zcsLen - 1] + "(双)");
                            } else {
                                zcBuffer.append(zcs[curr] + "-" + zcs[zcsLen - 1] + "(单)");
                            }
                        }else{
                            zcBuffer.append(zcs[curr] + "-" + zcs[zcsLen - 1]);
                        }
                    }
                }
            }
        } else {
            if (zcsLen == 1) {
                zcBuffer.append(zcs[curr]);
            } else {
                if (Integer.parseInt(zcs[curr]) + 1 == Integer.parseInt(zcs[curr + 1])) {
                    zcBuffer.append(zcs[curr] + "-" + zcs[curr + 1]);
                } else if (Integer.parseInt(zcs[curr]) + 2 == Integer.parseInt(zcs[curr + 1])) {
                    if (Integer.parseInt(zcs[curr]) % 2 == 0) {
                        zcBuffer.append(zcs[curr] + "-" + zcs[curr + 1] + "(双)");
                    } else {
                        zcBuffer.append(zcs[curr] + "-" + zcs[curr + 1] + "(单)");
                    }
                }
            }
        }
        System.out.println(zcBuffer);
        return zcBuffer.toString();
    }
}

package gka.controller;


public class CommonUtil {

    /**
     * 初始化课表二位数组
     *
     * @param kb
     * @return
     */
    public static String[][] initArray(String[][] kb) {
        for (int i = 0; i < kb.length; i++) {
            for (int j = 0; j < kb[i].length; j++) {
                kb[i][j] = "";
            }
        }
        return kb;
    }

    /**
     * 格式化周次
     *
     * @param zc
     * @return
     */
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
                        } else {
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
        return zcBuffer.toString();
    }
}

package gka.common.kit;

import gka.resource.properties.ProKit;

import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class OrderCodeFactory {
    //    private static final FastDateFormat pattern = FastDateFormat.getInstance("yyyyMMddHHmmss");
    private static final AtomicInteger atomicInteger = new AtomicInteger(1);
    private static ThreadLocal<StringBuilder> threadLocal = new ThreadLocal<StringBuilder>();
    private static String pre_fix = ProKit.use("gkean.properties").getStr("order_no_pre");
    /**
     * 【长码生成策略】
     *
     * @时间20180511231532
     * @二位随机数
     * @lock的hash-code编码
     * @param lock 生成的UUID32位参数
     * @return 长码机制
     */
//    public static String getC(String lock) {
//        StringBuilder builder = new StringBuilder(pattern.format(Instant.now().toEpochMilli()));// 取系统当前时间作为订单号前半部分
//        builder.append(Math.abs(lock.hashCode()));// HASH-CODE
//        builder.append(atomicInteger.getAndIncrement());// 自增顺序
//        threadLocal.set(builder);
//        return threadLocal.get().toString();
//    }

    /**
     * 【短码生成策略】
     *
     * @param lock
     * @return
     */
    public static String getD(String lock) {
        StringBuilder builder = new StringBuilder(ThreadLocalRandom.current().nextInt(0, 999));// 随机数
        builder.append(pre_fix);
        builder.append(Math.abs(lock.hashCode()));// HASH-CODE
        builder.append(System.currentTimeMillis());// 自增顺序
        threadLocal.set(builder);
        return threadLocal.get().toString();
    }

    public static void main(String[] args) {
        System.out.println(getD("1572183227625nichnye"));
    }

}
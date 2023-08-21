package pl.nehorowo.tools.utils;

import java.util.concurrent.ThreadLocalRandom;

public class RandomUtil {

    private static ThreadLocalRandom random = ThreadLocalRandom.current();

    public static int getRandomInt(int a, int b) {
        return random.nextInt(a, b);
    }
}

package com.ila.utilitys.random;


import java.util.Random;

public class random_number {
    private static volatile random_number instance;//volatile for fast synchronization
    private static int seed;
    private final Random random;
    private random_number(){
        random_number.seed = Math.toIntExact(System.currentTimeMillis());
        random = new Random();
        random.setSeed(seed);
    }
    public static random_number getInstance()//double check locking singleton creation
    {
        if (instance == null)
            synchronized (random_number.class)
            {
                if(instance == null)
                {
                    instance = new random_number();
                }
            }
        return instance;
    }
    public int getRandom(int max)
    {
        return random.nextInt(max);
    }
    public int getRandom(int max, boolean signed, int probability)
    {
        int result = getRandom(max);
        if(signed)
        {
            if(probability > 100 || probability < 1) {return 0;}
            else if(getRandom(100) < probability)
            {
                result = -result;
            }
        }
        return result;
    }



}

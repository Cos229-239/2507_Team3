package com.ila.utilitys.random;

public class gameId {
    private static int last_id;
    static int getNextId()
    {
        last_id = last_id +1;
        return last_id;
    }

}

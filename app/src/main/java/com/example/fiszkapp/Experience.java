package com.example.fiszkapp;

public class Experience {
    //liczy ile exp potrzeba od poziomu level do level+1
    public static int calculateNeededExp(int level)
    {
        return 1000 + 250*(level-1);
    }

    //mając dany level zwróć na którym levelu jesteśmy
    public static int calculateExpFromCurrentLevel(int exp, int level)
    {
        int expBeforeLevel = totalExpToGetLevel(level);
        return exp-expBeforeLevel;
    }

    public static int totalExpToGetLevel(int level)
    {
        int a1 = 1000;
        int an = calculateNeededExp(level-1);
        return (a1+an)/2*(level-1);
    }

    public static int expToNextLevel(int exp, int level)
    {
        int expToLevel=calculateNeededExp(level);
        int currentexp=calculateExpFromCurrentLevel(exp, level);
        return expToLevel - currentexp;
    }

    public static int addExp(int exp, int gainedExp)
    {
        return exp + gainedExp;
    }

    public static int calculateLevel(int exp, int level) //level up when exp for next level reached
    {
        if(exp>=totalExpToGetLevel(level+1))
        {
            level++;
        }
        return level;
    }
}

package com.tu.FinancialQuickCheck;

import java.util.HashMap;
import java.util.Map;

/**
 * Ratings like high, medium and low are possible for the economic and complexity evaluation.
 */
public enum Score {
    HOCH(3), MITTEL(2), GERING(1);

    private int value;
    private static Map map = new HashMap<>();
    Score(int value) {
        this.value = value;
    }

    static {
        for (Score score : Score.values()) {
            map.put(score.value, score);
        }
    }

    public static Score valueOf(int score) {
        return (Score) map.get(score);
    }

    public int getValue() {
        return value;
    }
}

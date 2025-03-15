package com.steve.aqrender.shader.program;

public record Uniform(Integer location, TYPE type) {
    /**
     * Uniform的类型
     */
    public enum TYPE {
        ONE_INTEGER,
        ONE_FLOAT,
        FOUR_FLOAT_MATRIX,
        UNDEFINED
    }
}

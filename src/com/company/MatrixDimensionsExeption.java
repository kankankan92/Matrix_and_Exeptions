package com.company;

public class MatrixDimensionsExeption extends RuntimeException {

    /*
    Dimensions - размеры.
     */
    public MatrixDimensionsExeption(String string) {
        super(string);
    }

    public MatrixDimensionsExeption() {
    }
}

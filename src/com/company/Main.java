package com.company;

public class Main {

    public static void main(String[] args) {
        Matrix matrixA = new Matrix(2, 2);
        matrixA.fillMatrix(1, 2, 3, 4);
        Matrix matrixB = new Matrix(2, 2);
        matrixB.fillMatrix(2,1,0,0);
//        Matrix matrixC = matrixA.multiplication(matrixB);
//        Matrix matrixE = new Matrix(matrixA);
        double a = matrixA.matrixDeterminant();
        double b = matrixB.matrixDeterminant();
        Matrix matrixD = null;
        Matrix matrixF = null;
        try {
            matrixD = matrixA.invertMatrix();
            matrixF = matrixB.invertMatrix();

        }catch (MatrixDeterminantExeption matrixDeterminantExeption){
            System.out.println("Определитель матрицы равен нулю");
        }finally {
            matrixA.print();
            matrixD.print();
            matrixB.print();
            try {
                matrixF.print();
            }catch (NullPointerException n){
                System.out.println("NullPointerExeption");
            }
        }

//        matrixE.print();
    }
}

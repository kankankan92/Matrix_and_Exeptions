package com.company;

public class Matrix {


    private double[][] matrix = null;
    private int lines;
    private int columns;

    Matrix(int lines, int columns) {
        matrix = new double[lines][columns];
        this.lines = lines;
        this.columns = columns;
    }

    Matrix(Matrix matrix){
        this.matrix = new double[matrix.lines][matrix.columns];
        this.lines = matrix.lines;
        this.columns = matrix.columns;
        for (int i = 0; i < lines; i++) {
            System.arraycopy(matrix.matrix[i], 0, this.matrix[i], 0, columns);
        }
    }

    public void fillMatrix(int... value) {
        int k = 0;
        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < columns; j++) {
                matrix[i][j] = value[k];
                k++;
            }
        }
    }

    public Matrix multiplication(Matrix matrix) throws MatrixDimensionsExeption {
        if (columns == matrix.lines) {
            Matrix resultMatrix = new Matrix(lines, matrix.columns);
            for (int i = 0; i < lines; i++) {
                for (int j = 0; j < matrix.columns; j++) {
                    int result = 0;
                    for (int k = 0; k < matrix.lines; k++) {
                        result += this.matrix[i][k] * matrix.matrix[k][j];
                    }
                    resultMatrix.matrix[i][j] = result;
                }
            }
            return resultMatrix;
        } else {
            throw new MatrixDimensionsExeption();
        }
    }

    public void print() {
        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < columns; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public double matrixDeterminant(){
        if(lines == columns) {
            Matrix temporary;
            double result = 0;

            if (matrix.length == 1) {
                result = matrix[0][0];
                return result;
            }

            if (matrix.length == 2) {
                result = ((matrix[0][0] * matrix[1][1]) - (matrix[0][1] * matrix[1][0]));
                return result;
            }

            for (int i = 0; i < matrix[0].length; i++) {
                temporary = new Matrix(matrix.length - 1,matrix[0].length - 1);

                for (int j = 1; j < matrix.length; j++) {
                    for (int k = 0; k < matrix[0].length; k++) {
                        if (k < i) {
                            temporary.matrix[j - 1][k] = matrix[j][k];
                        } else if (k > i) {
                            temporary.matrix[j - 1][k - 1] = matrix[j][k];
                        }
                    }
                }

                result += matrix[0][i] * Math.pow(-1, (double) i) * temporary.matrixDeterminant();
            }
            return result;
        } else {
            throw new MatrixDimensionsExeption();
        }
    }

    public  Matrix invertMatrix() throws MatrixDeterminantExeption {
        if (matrixDeterminant() != 0) {
            Matrix auxiliaryMatrix, invertedMatrix;
            int[] index;
            auxiliaryMatrix = new Matrix(matrix.length, matrix.length);
            invertedMatrix = new Matrix(matrix.length, matrix.length);
            index = new int[matrix.length];

            for (int i = 0; i < matrix.length; ++i) {
                auxiliaryMatrix.matrix[i][i] = 1;
            }

            Matrix transformed = transformToUpperTriangle(index);

            for (int i = 0; i < (transformed.matrix.length - 1); ++i) {
                for (int j = (i + 1); j < transformed.matrix.length; ++j) {
                    for (int k = 0; k < transformed.matrix.length; ++k) {
                        auxiliaryMatrix.matrix[index[j]][k] -= transformed.matrix[index[j]][i] * auxiliaryMatrix.matrix[index[i]][k];
                    }
                }
            }

            for (int i = 0; i < transformed.matrix.length; ++i) {
                invertedMatrix.matrix[transformed.matrix.length - 1][i] = (auxiliaryMatrix.matrix[index[transformed.matrix.length - 1]][i] / transformed.matrix[index[transformed.matrix.length - 1]][transformed.matrix.length - 1]);

                for (int j = (transformed.matrix.length - 2); j >= 0; --j) {
                    invertedMatrix.matrix[j][i] = auxiliaryMatrix.matrix[index[j]][i];

                    for (int k = (j + 1); k < transformed.matrix.length; ++k) {
                        invertedMatrix.matrix[j][i] -= (transformed.matrix[index[j]][k] * invertedMatrix.matrix[k][i]);
                    }

                    invertedMatrix.matrix[j][i] /= transformed.matrix[index[j]][j];
                }
            }

            return (invertedMatrix);
        }else{
            throw new MatrixDeterminantExeption();
        }
    }

    private Matrix transformToUpperTriangle(int[] index) {
        Matrix transformed = new Matrix(this);
        double[] c;
        double c0, c1, pi0, pi1, pj;
        int itmp, k;

        c = new double[transformed.matrix.length];

        for (int i = 0; i < transformed.matrix.length; ++i) {
            index[i] = i;
        }

        for (int i = 0; i < transformed.matrix.length; ++i) {
            c1 = 0;

            for (int j = 0; j < transformed.matrix.length; ++j) {
                c0 = Math.abs(transformed.matrix[i][j]);

                if (c0 > c1) {
                    c1 = c0;
                }
            }

            c[i] = c1;
        }

        k = 0;

        for (int j = 0; j < (transformed.matrix.length - 1); ++j) {
            pi1 = 0;

            for (int i = j; i < transformed.matrix.length; ++i) {
                pi0 = Math.abs(transformed.matrix[index[i]][j]);
                pi0 /= c[index[i]];

                if (pi0 > pi1) {
                    pi1 = pi0;
                    k = i;
                }
            }

            itmp = index[j];
            index[j] = index[k];
            index[k] = itmp;

            for (int i = (j + 1); i < transformed.matrix.length; ++i) {
                pj = transformed.matrix[index[i]][j] / transformed.matrix[index[j]][j];
                transformed.matrix[index[i]][j] = pj;

                for (int l = (j + 1); l < transformed.matrix.length; ++l) {
                    transformed.matrix[index[i]][l] -= pj * transformed.matrix[index[j]][l];
                }
            }
        }
        return transformed;
    }
}

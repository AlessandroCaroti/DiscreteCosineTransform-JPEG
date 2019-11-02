import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class DCTv3 {
    public static void main(String[] args) throws IOException {
        System.out.println("ALGORITMO DCT v3.0\n\n");

        double[][] image = loadImage("C:\\imagePath");
        double[][] result = DCT_V3(image);
        printMatrix(image);
        printMatrix(result);
        System.in.read();
    }

    private static int N = 8;   //BLOCK SIZE

    static private double[][] DCT_V3(double[][] f) {
        double[][] DCT = new double[N][N];
        double[][] DCT2 = new double[N][N];
        double[][] A = dct_kernelMatrix();

        //DCT = A*f
        for (int u = 0; u < N; u++) {
            for (int v = 0; v < N; v++) {
                for (int k = 0; k < N; k++) {
                    DCT[u][v] += A[u][k] * f[k][v];
                }
            }
        }

        //DCT2 = DCT*A'
        double[][] A_t = transposeMatrix(A);
        for (int u = 0; u < N; u++) {
            for (int v = 0; v < N; v++) {
                for (int k = 0; k < N; k++) {
                    DCT2[u][v] += DCT[u][k] * A_t[k][v];
                }
            }
        }

        return DCT2;
    }

    static private double[][] iDCT_V3(double[][] T) {
        double[][] _f = new double[N][N];
        double[][] f = new double[N][N];
        double[][] A = dct_kernelMatrix();
        double[][] A_t = transposeMatrix(A);

        //_f = A'*T
        for (int u = 0; u < N; u++) {
            for (int v = 0; v < N; v++) {
                for (int k = 0; k < N; k++) {
                    _f[u][v] += A_t[u][k] * T[k][v];
                }
            }
        }

        //f = _f * A
        for (int u = 0; u < N; u++) {
            for (int v = 0; v < N; v++) {
                for (int k = 0; k < N; k++) {
                    f[u][v] += _f[u][k] * A[k][v];
                }
            }
        }

        return f;
    }


    static private double[][] dct_kernelMatrix() {
        double[][] KM = new double[N][N];
        double alpha1 = 1 / Math.sqrt(N);
        double alpha2 = Math.sqrt(2.0 / N);

        for (int q = 0; q < N; q++) {
            KM[0][q] = alpha1;
        }
        for (int p = 1; p < N; p++) {
            for (int q = 0; q < N; q++) {
                KM[p][q] = alpha2 *
                        Math.cos((Math.PI * (double) (2 * q + 1) * (double) p) / (double) (2 * N));
            }
        }
        return KM;
    }


    //UTILITY FUNCTION
    static private void printMatrix(double[][] DCT_matrix) {
        System.out.print("[");
        NumberFormat formatter = new DecimalFormat("#0.0000");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(formatter.format(DCT_matrix[i][j]) + " ");
            }
            if (i != N - 1)
                System.out.println();
        }
        System.out.println("]\n");
    }

    static private double[][] loadImage(String path) {
        return testMatrix2;
    }

    private static double[][] transposeMatrix(double[][] m) {
        double[][] temp = new double[m[0].length][m.length];
        for (int i = 0; i < m.length; i++)
            for (int j = 0; j < m[0].length; j++)
                temp[j][i] = m[i][j];
        return temp;
    }

    private static double[][] testMatrix = {{139., 144., 149., 153., 155., 155., 155., 155.},
            {144., 151., 153., 156., 159., 156., 156., 156.},
            {150., 155., 160., 163., 158., 156., 156., 156.},
            {159., 161., 162., 160., 160., 159., 159., 159.},
            {159., 160., 161., 162., 162., 155., 155., 155.},
            {161., 161., 161., 161., 160., 157., 157., 157.},
            {162., 162., 161., 163., 162., 157., 157., 157.},
            {162., 162., 161., 161., 163., 158., 158., 158.}};
    private static double[][] testMatrix2 = {{-13, -16, -16, -16, -20, -28, -32, -30},
            {-15, -18, -19, -18, -21, -28, -31, -29},
            {-28, -30, -31, -30, -32, -36, -38, -36},
            {-42, -44, -45, -43, -43, -44, -43, -41},
            {-42, -44, -44, -42, -39, -37, -35, -34},
            {-30, -31, -31, -28, -25, -21, -20, -19},
            {-12, -13, -13, -11, -7, -4, -3, -4},
            {3, 2, 2, 3, 7, 9, 9, 7}};

    private static double[][] testMatrix3 = {{26, -5, -5, -5, -5, -5, -5, 8},
            {64, 52, 8, 26, 26, 26, 8, -18},
            {126, 70, 26, 26, 52, 26, -5, -5},
            {111, 52, 8, 52, 52, 38, -5, -5},
            {52, 26, 8, 39, 38, 21, 8, 8},
            {0, 8, -5, 8, 26, 52, 70, 26},
            {-5, -23, -18, 21, 8, 8, 52, 38},
            {-18, 8, -5, -5, -5, 8, 26, 8}};


//    private static double[][] testMatrix = {{1., 2., 2., 0.},
//            {0., 1., 3., 1.},
//            {0., 1., 2., 1.},
//            {1., 2., 2., -1.}};


}

import java.io.IOException;

public class DCTv2_2 {
    public static void main(String[] args) throws IOException {
        System.out.println("ALGORITMO DCT v2.2\n\n");
        double[][] image = loadImage("C:\\imagePath");
        double[][] result = DCT_V2_2(image);
        printMatrix(image);
        printMatrix(result);
        System.in.read();
    }

    private static int N = 8;   //BLOCK SIZE

    static private double[][] DCT_V2_2(double[][] dataInput) {
        double[][] DCT = new double[N][N];
        double[][] DCT2 = new double[N][N];
        double[] row_in, column_in;
        double[] row_out, column_out;

        for (int j = 0; j < N; j++) {
            column_in = getColumn(dataInput, j);
            column_out = dct_1d(column_in);
            setColumn(DCT, column_out, j);
        }

        for (int j = 0; j < N; j++) {
            row_in = getRow(DCT, j);
            row_out = dct_1d(row_in);
            setRow(DCT2, row_out, j);
        }
        return DCT2;
    }

    static private double[] dct_1d(double[] in) {
        double[] out = new double[N];
        double sum;
        for (int u = 0; u < N; u++) {
            sum = 0.;
            for (int x = 0; x < N; x++) {
                sum += in[x] *
                        Math.cos((Math.PI * (2.0 * x + 1.0) * (double) u) / (2.0 * N));
            }
            out[u] = alpha(u) * sum;
        }
        return out;
    }

    static private double[] getRow(double[][] matrix, int rowNumber) {
        double[] res = new double[N];
        System.arraycopy(matrix[rowNumber], 0, res, 0, N);
        return res;
    }

    static private double[] getColumn(double[][] matrix, int columnNumber) {
        double[] res = new double[N];
        for (int i = 0; i < N; i++) {
            res[i] = matrix[i][columnNumber];
        }
        return res;
    }

    static private void setRow(double[][] matrixDest, double[] row, int rowNumber) {
        for (int i = 0; i < N; i++) {
            matrixDest[rowNumber][i] = row[i];
        }
    }

    static private void setColumn(double[][] matrixDest, double[] column, int columnNumber) {
        for (int i = 0; i < N; i++) {
            matrixDest[i][columnNumber] = column[i];
        }
    }


    static private double alpha(int u) {
        if (u == 0)
            return Math.sqrt(1.0 / (double) N);
        return Math.sqrt(2.0 / (double) N);
    }


    //UTILITY FUNCTION
    static private void printMatrix(double[][] DCT_matrix) {
        System.out.print("[");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(DCT_matrix[i][j] + " ");
            }
            if (i != N - 1)
                System.out.println();
        }
        System.out.println("]\n");
    }

    static private double[][] loadImage(String path) {
        return testMatrix2;
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
}

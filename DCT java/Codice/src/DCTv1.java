import java.io.IOException;

public class DCTv1 {

    public static void main(String[] args) throws IOException {
        System.out.println("ALGORITMO DCT v1.0\n\n");
        double[][] image = loadImage("C:\\imagePath");
        double[][] result = DCT_V1(image);
        printMatrix(image);
        printMatrix(result);
        System.in.read();
    }

    private static int N = 8;   //BLOCK SIZE

    static private double[][] DCT_V1(double[][] dataInput) {
        double[][] DCT = new double[N][N];

        double sum;
        for (int u = 0; u < N; u++) {
            for (int v = 0; v < N; v++) {
                sum = 0.0;
                for (int x = 0; x < N; x++) {
                    for (int y = 0; y < N; y++) {
                        sum += dataInput[x][y] *
                                Math.cos((Math.PI * (double) (2 * x + 1) * (double) u) / (double) (2 * N)) *
                                Math.cos((Math.PI * (double) (2 * y + 1) * (double) v) / (double) (2 * N));
                    }
                }
                DCT[u][v] = alpha(u) * alpha(v) * sum;
            }
        }
        return DCT;
    }

    static private double alpha(int u) {
        if (u == 0)
            return 1.0 / Math.sqrt(N);
        return Math.sqrt(2.0 / N);
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
        return testMatrix;
    }
///*
    private static double[][] testMatrix = {{139., 144., 149., 153., 155., 155., 155., 155.},
            {144., 151., 153., 156., 159., 156., 156., 156.},
            {150., 155., 160., 163., 158., 156., 156., 156.},
            {159., 161., 162., 160., 160., 159., 159., 159.},
            {159., 160., 161., 162., 162., 155., 155., 155.},
            {161., 161., 161., 161., 160., 157., 157., 157.},
            {162., 162., 161., 163., 162., 157., 157., 157.},
            {162., 162., 161., 161., 163., 158., 158., 158.}};
//*/
//    private static double[][] testMatrix = {{1.,2.,2.,0.},
//        {0.,1.,3.,1.},
//        {0.,1.,2.,1.},
//        {1.,2.,2.,-1.}};

}

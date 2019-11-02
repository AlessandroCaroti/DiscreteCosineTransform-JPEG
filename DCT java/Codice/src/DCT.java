public class DCT {
    private double[][] km;  //kernel matrix
    private int N;

    DCT(int n) {

        N = n;
        km = dct_kernelMatrix();
    }

    double[][] DCT_V3(double[][] f) {
        double[][] DCT = new double[N][N];
        double[][] DCT2 = new double[N][N];

        for (int u = 0; u < N; u++) {
            for (int v = 0; v < N; v++) {
                for (int k = 0; k < N; k++) {
                    DCT[u][v] += km[u][k]*f[k][v];
                }
            }
        }

        for (int u = 0; u < N; u++) {
            for (int v = 0; v < N; v++) {
                for (int k = 0; k < N; k++) {
                    DCT2[u][v] += DCT[u][k]*km[v][k];
                }
            }
        }

        return DCT2;
    }

    double[][] iDCT_V3(double[][] f) {
        double[][] DCT = new double[N][N];
        double[][] DCT2 = new double[N][N];

        for (int u = 0; u < N; u++) {
            for (int v = 0; v < N; v++) {
                for (int k = 0; k < N; k++) {
                    DCT[u][v] += km[k][u]*f[k][v];
                }
            }
        }

        for (int u = 0; u < N; u++) {
            for (int v = 0; v < N; v++) {
                for (int k = 0; k < N; k++) {
                    DCT2[u][v] += DCT[u][k]*km[k][v];
                }
            }
        }

        return DCT2;
    }


    private double[][] dct_kernelMatrix() {
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


}

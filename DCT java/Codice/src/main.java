import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;

public class main {

    static int N = 8;

    public static void main(String[] args) throws Exception {
        String path = "C:\\Users\\carot\\Dropbox\\Università\\FESI\\Progetto\\Codice\\DCT 2-D - FESI\\DCT 2-D\\img\\";
        testRealImage(path, "IMG.jpg");
        testRandomMatrix(path);
    }

    private static void testRandomMatrix(String dirPath) throws Exception {
        //CREATE AND SAVE RANDOM IMAGE
        int[][] img = createRandomImage(N,N,dirPath+"RANDOM_IMG.jpg");
        testRealImage(dirPath, "RANDOM_IMG.jpg");
    }

    private static void testRealImage(String dirPath, String fileName) throws Exception {
        int[][] original_img = loadImage(dirPath+fileName);
        saveImage(original_img,dirPath+"GRAY_"+fileName);
        int[][] img_shifted = shift(-128, original_img);

        DCT dct = new DCT(original_img.length);
        double[][] dct_img = dct.DCT_V3(toDouble(img_shifted));
        double[][] idct_img = dct.iDCT_V3(dct_img);

        int[][] img_ = shift(128,toInt(idct_img));
        saveImage(img_,dirPath+"ricostruita_"+fileName);

        System.out.println("IMMAGINE ORIGINALE:");
        printMatrix(original_img);
        System.out.println("DCT DELL'IMMAGINE CON COEFICENTI ABBASSATI DI 128:");
        printMatrix(dct_img);
        System.out.println("IMMAGINE RICOSTRUITA DALLA DCT:");
        printMatrix(img_);
        System.out.println("DIFFERENZA TRA L'IMMAGINE ORIGINALE E QUELLA TRASFORMATA:");
        compareMatrix(original_img, img_);



        double[][] dct_imgM = dctToImage(dct_img);
        saveImage(toInt(dct_imgM),dirPath+"DCT_AS_IMG__"+fileName);

    }

    private static int[][] loadImage(String path) throws IOException {
        File file = new File(path);
        BufferedImage img = ImageIO.read(file);
        int width = img.getWidth();
        int height = img.getHeight();
        int[][] imgArr = new int[width][height];
        Raster raster = img.getData();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                imgArr[i][j] = raster.getSample(i, j, 0);
            }
        }
        return imgArr;
    }

    private static void saveImage(int[][] imgArr, String path) {
        try {
            BufferedImage image = new BufferedImage(imgArr.length, imgArr[0].length, BufferedImage.TYPE_BYTE_GRAY);
            for (int i = 0; i < imgArr.length; i++) {
                for (int j = 0; j < imgArr[0].length; j++) {
                    int a = imgArr[i][j];
                    if (a < 0 || a > 256)
                        throw new IllegalArgumentException("il valore deve essere in un range tra 0 e 256. Valore: " + a);
                    Color newColor = new Color(a, a, a);
                    image.setRGB(j, i, newColor.getRGB());
                }
            }
            File output = new File(path);
            ImageIO.write(image, "jpg", output);
        } catch (IOException | IllegalArgumentException e) {
            e.getMessage();
        }
    }

    static private void printMatrix(int[][] matrix) {
        System.out.print("[");
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.printf("%4d", matrix[i][j]);
            }
            if (i != matrix.length - 1)
                System.out.println();
        }
        System.out.println("]\n");
    }

    static private void printMatrix(double[][] matrix) {
        System.out.print("[");
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.printf("%.3f",matrix[i][j]);
                System.out.print(" ");
//                System.out.print(matrix[i][j] + " ");
            }
            if (i != matrix.length - 1)
                System.out.println();
        }
        System.out.println("]\n");
    }

    static private double[][] toDouble(int[][] intMatrix) {
        int width = intMatrix.length;
        int height = intMatrix[0].length;
        double[][] doubleMatrix = new double[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                doubleMatrix[i][j] = intMatrix[i][j];
            }
        }
        return doubleMatrix;
    }

    static private int[][] toInt(double[][] doubleMatrix) {
        int width = doubleMatrix.length;
        int height = doubleMatrix[0].length;
        int[][] intMatrix = new int[width][height];
        double d;
        int ii;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {

                d = doubleMatrix[i][j];
                ii = (int) d;
                intMatrix[i][j] = ii;
            }
        }
        return intMatrix;
    }

    static private int[][] shift(int n, int[][] matrix) {
        int width = matrix.length;
        int height = matrix[0].length;
        int[][] resoult = new int[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                resoult[i][j] = matrix[i][j] + n;
            }
        }
        return resoult;
    }

    static private int[][] createRandomImage(int w, int h,String path){
        int[][] rndImgMatrix = new int[w][h];
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                rndImgMatrix[i][j] = (int) (Math.random()*256);
            }
        }
        saveImage(rndImgMatrix,path);
        return rndImgMatrix;
    }

    static private void compareMatrix(double[][] m1, double[][] m2) throws Exception {
        if(m1.length!=m2.length || m1[0].length!=m2[0].length)
            throw new Exception();
        int width = m1.length;
        int height = m1[0].length;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                System.out.printf("%.16f",Math.abs((m1[i][j]-m2[i][j])));
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    static private void compareMatrix(int [][] m1, int[][] m2) throws Exception {
        if(m1.length!=m2.length || m1[0].length!=m2[0].length)
            throw new Exception();
        int width = m1.length;
        int height = m1[0].length;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                System.out.print(Math.abs((m1[i][j]-m2[i][j])) + " ");
            }
            System.out.println();
        }
    }

    private static double[][] dctToImage(double[][] dctMatrix){
        double[] maxMin = getMaxMin(dctMatrix);
        double OldRange = (maxMin[0] - maxMin[1]);
        double NewRange = 255;
        int width = dctMatrix.length;
        int height = dctMatrix[0].length;
        double[][] dctImageMatrix = new double[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                dctImageMatrix[i][j] = Math.abs((((Math.abs(dctMatrix[i][j]) - maxMin[1]) * NewRange) / OldRange) + 0);
            }
        }
        return dctImageMatrix;
    }

    private static double[] getMaxMin(double[][] m){
        double max = Math.abs(m[0][0]);
        double min = Math.abs(m[0][0]);
        int width = m.length;
        int height = m[0].length;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if(Math.abs(m[i][j])<min)
                    min = Math.abs(m[i][j]);
                else if(Math.abs(m[i][j])>max)
                    max = Math.abs(m[i][j]);
            }
        }
        double[] out = new double[2];
        out[0] = max;
        out[1] = min;
        return out;
    }


}

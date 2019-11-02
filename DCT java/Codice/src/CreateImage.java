import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CreateImage {
    static String path = "D:\\Programmi\\Dropbox\\Università\\Tesi 2019\\";
    static String fileName1 = "decompressa.jpg";
    static int[][] matrix1 = {{111, 103, 101, 99, 99, 101, 107, 117},
            {102, 91, 89, 88, 89, 93, 102, 114},
            {93, 79, 81, 84, 90, 97, 106, 117},
            {81, 64, 69, 78, 88, 99, 109, 119},
            {89,75,82,91,101,111,118,124},
            {118,115,119,124,129,133,133,131},
            {139,144,146,147,147,145,141,135},
            {139,144,144,144,143,140,137,133}};
    static String fileName2 = "originale.jpg";
    static int[][] matrix2 = {{115, 112, 112, 112, 108, 100,  96,  98},
            {113, 110, 109, 110, 107, 100,  97,  99},
            {100,  98,  97,  98,  96,  92,  90,  92},
            { 86,  84,  83,  85,  85,  84,  85,  87},
            { 86,  84,  84,  86,  89,  91,  93,  94},
            { 98,  97,  97, 100, 103, 107, 108, 109},
            {116, 115, 115, 117, 121, 124, 125, 124},
            {131, 130, 130, 131, 135, 137, 137, 135}};

    public static void main(String[] args) {
        saveImage(matrix1, path+fileName1);
        saveImage(matrix2, path+fileName2);
        int[][] m3 = new int[16][16];
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                m3[i][j] = j + 16*i;
            }
        }
        saveImage(m3,path+"mmmm.jpg");
    }

    private static void saveImage(int[][] imgArr, String path) {
        try {
            BufferedImage image = new BufferedImage(imgArr.length, imgArr[0].length, BufferedImage.TYPE_BYTE_GRAY);
            for (int i = 0; i < imgArr.length; i++) {
                for (int j = 0; j < imgArr[0].length; j++) {
                    int a = imgArr[i][j];
                    if (a < 0 || a > 256)
                        throw new IllegalArgumentException("il valore deve essere in un range tra 0 e 256. Valore: " + a+"pos"+i+","+j);
                    Color newColor = new Color(a, a, a);
                    image.setRGB(j, i, newColor.getRGB());
                }
            }
            File output = new File(path);
            ImageIO.write(image, "jpg", output);
        } catch (IOException | IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }


}

package ca.leblanc.serge; /**
 * Created by Serge LeBlanc on 12/17/2015.
 */

import java.io.File;
import java.util.Scanner;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Hashtable;
import java.util.UUID;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class MainGenerator {

    public static void main(String[] args) {
        String URL = "";
        while (!URL.equals("exit")) {

            Scanner scanner = new Scanner(System.in);

            System.out.println("Hello Enter your URL: ");

            URL = scanner.nextLine();

            if (URL.equals("exit"))
                break;

            System.out.println("URL is: " + URL);
            generateQR(URL);

        }

    }

    private static void generateQR(String URL) {

        int size = 250;
        String fileType = "jpg";
        String uniqueID = UUID.randomUUID().toString()+"."+fileType;
        File myFile = new File(uniqueID);
        try {

            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix byteMatrix = qrCodeWriter.encode(URL, BarcodeFormat.QR_CODE, size, size);

            BufferedImage image = new BufferedImage(byteMatrix.getWidth(), byteMatrix.getHeight(), BufferedImage.TYPE_INT_RGB);
            image.createGraphics();

            Graphics2D graphics = (Graphics2D) image.getGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, byteMatrix.getWidth(), byteMatrix.getHeight());
            graphics.setColor(Color.BLACK);

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (byteMatrix.get(i, j)) {
                        graphics.fillRect(i, j, 1, 1);
                    }
                }
            }
            ImageIO.write(image, fileType, myFile);
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Your QR code has been created under the name: "+uniqueID);
    }
}

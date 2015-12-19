package ca.leblanc.serge;

import java.io.File;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.UUID;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;


/** QrGenerator is an a class that Generates QR codes based on the URL, File Type and Color provided.
 *
 * @author Serge C LeBlanc
 * @version 1
 */
public class QrGenerator {
    /** main is the first class to be executed and launches the swing form.
     *
     *
     * @param args This would be the typical args when running the class.
     */
    public static void main(String[] args) {
        QRGenUi form = new QRGenUi();

        /*String URL = "";
        while (!URL.equals("exit")) {

            Scanner scanner = new Scanner(System.in);

            System.out.println("Hello Enter your URL: ");

            URL = scanner.nextLine();

            if (URL.equals("exit"))
                break;

            System.out.println("URL is: " + URL);
            generateQR(URL);

        }*/

    }

    /**
     * This is the method that generates the QR code.
     * Once the QR is generated and saved it places the image at the root lvl of the application.
     *
     * @param URL This is to set the URL that will be used to generate the QR Code.
     * @param fileType This is to set the File type of the image you wish to save ie: .jpg, .png, .gif.
     * @param color This is to set the color of the QR code.
     */
    public static void generateQR(String URL, String fileType, Color color, int size) {
        String uniqueID = UUID.randomUUID().toString()+fileType;
        File myFile = new File(uniqueID);
        try {

            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix byteMatrix = qrCodeWriter.encode(URL, BarcodeFormat.QR_CODE, size, size);

            BufferedImage image = new BufferedImage(byteMatrix.getWidth(), byteMatrix.getHeight(), BufferedImage.TYPE_INT_RGB);
            image.createGraphics();

            Graphics2D graphics = (Graphics2D) image.getGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, byteMatrix.getWidth(), byteMatrix.getHeight());
            graphics.setColor(color);

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (byteMatrix.get(i, j)) {
                        graphics.fillRect(i, j, 1, 1);
                    }
                }
            }
            ImageIO.write(image, fileType.replace(".",""), myFile);
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Your QR code has been created under the name: "+uniqueID);
    }
}

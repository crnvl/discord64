package Core;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.security.MessageDigest;

public class Conv {

    public static String getMD5(String url) throws Exception {
        //File input = new File("C:/image.png");
        URL u = new URL(url);

        BufferedImage buffImg = ImageIO.read(u);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(buffImg, "png", outputStream);
        byte[] data = outputStream.toByteArray();

        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(data);
        byte[] hash = md.digest();
        return returnHex(hash).replaceFirst("null", "");
    }

    // Below method of converting Byte Array to hex
    // Can be found at: http://www.rgagnon.com/javadetails/java-0596.html
    static String returnHex(byte[] inBytes) throws Exception {
        String hexString = null;
        for (int i=0; i < inBytes.length; i++) { //for loop ID:1
            hexString +=
                    Integer.toString( ( inBytes[i] & 0xff ) + 0x100, 16).substring( 1 );
        }                                   // Belongs to for loop ID:1
        return hexString;
    }

}

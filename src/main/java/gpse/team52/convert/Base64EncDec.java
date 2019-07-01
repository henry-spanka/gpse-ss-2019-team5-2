package gpse.team52.convert;

import java.io.*;
import java.util.Base64;

import org.springframework.web.multipart.MultipartFile;

/**
 * Base64 encoder and decoder.
 */
public class Base64EncDec {



   /* public String setDefaultPic() {
        String def = "";
        try {
            URL res = getClass().getClassLoader().getResource("user_profile_default.jpg");
            File pic = Paths.get(res.toURI()).toFile();
            def = encoder(pic.getAbsolutePath());
        } catch(Exception e) {
            // Not an issue since the default picture is always in Resources
        }
        return def;
    }*/

    /**
     * Encode an Image.
     * @param imagePath Path to Image.
     * @return Image in Base64 format.
     */
    public static String encoder(String imagePath) {
        String base64Image = "";
        File file = new File(imagePath);
        try (FileInputStream imageInFile = new FileInputStream(file)) {
            // Reading a Image file from file system
            byte[] imageData = new byte[(int) file.length()];
            imageInFile.read(imageData);
            base64Image = Base64.getEncoder().encodeToString(imageData);
        } catch (FileNotFoundException e) {
            System.out.println("Image not found" + e);
        } catch (IOException ioe) {
            System.out.println("Exception while reading the Image " + ioe);
        }
        return base64Image;
    }

    /**
     * Decode an Image.
     * @param base64Image Image in base64 format.
     * @param pathFile path to save image.
     */
    public static void decoder(String base64Image, String pathFile) {
        try (FileOutputStream imageOutFile = new FileOutputStream(pathFile)) {
            // Converting a Base64 String into Image byte array
            byte[] imageByteArray = Base64.getDecoder().decode(base64Image);
            imageOutFile.write(imageByteArray);
        } catch (FileNotFoundException e) {
            System.out.println("Image not found" + e);
        } catch (IOException ioe) {
            System.out.println("Exception while reading the Image " + ioe);
        }
    }

    /**
     * Concert Multipart file.
     * @param file MultipartFile.
     * @return File.
     */
    public static File convertToFile(MultipartFile file) {
        try {
            File convFile = new File(file.getOriginalFilename());
            convFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();
            return convFile;
        } catch (Exception e) {
            //
        }
        return  null;
    }


}

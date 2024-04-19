package com.datn.monolithic.service;

import com.datn.monolithic.entity.Picture;
import com.datn.monolithic.payload.response.Res;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Collections;


@Service
public class PictureIMPL implements PictureService {

    private static final JsonFactory JSON_Factory = GsonFactory.getDefaultInstance();

    private static final  String SERVICE_ACCOUNT_KEY_PATH = getPathToGoogleCredentials();

    private static String getPathToGoogleCredentials() {
        String currentDir = System.getProperty("user.dir");
        Path filePath = Paths.get(currentDir, "cred.json");
        return filePath.toString();
    }

    public Res uploadImageToDrive(File file) throws GeneralSecurityException, IOException {
        Res res = new Res();
        try {
            String folderId = "1WRrvdfQpujWx7fQ5xYfapL9gIym2Xprs";
            Drive drive = createDriveService();

            com.google.api.services.drive.model.File fileMetaData = new com.google.api.services.drive.model.File();
            fileMetaData.setName(file.getName());
            fileMetaData.setParents(Collections.singletonList(folderId));
            FileContent mediaContent = new FileContent("image/jpeg", file);

            com.google.api.services.drive.model.File uploadFile = drive.files().create(fileMetaData, mediaContent)
                    .setFields("id").execute();

            String imageUrl = "https://drive.google.com/uc?export=view&id=" + uploadFile.getId();

            res.setStatus(200);
            res.setMessage("Image Successfully Uploaded To Drive");
            res.setUrl(imageUrl);

        }catch (Exception e){
            System.out.println(e.getMessage());
            res.setStatus(500);
            res.setMessage(e.getMessage());
        }
        return res;
    }

    private Drive createDriveService() throws IOException, GeneralSecurityException {

        HttpRequestInitializer requestInitializer = httpRequest -> {
            GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(SERVICE_ACCOUNT_KEY_PATH))
                    .createScoped(Collections.singleton(DriveScopes.DRIVE));
            credentials.refreshIfExpired();
            HttpCredentialsAdapter adapter = new HttpCredentialsAdapter(credentials);
            adapter.initialize(httpRequest);
        };
        return new Drive.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_Factory,
                requestInitializer)
                .build();
    }


    @Override
    public String convertToBlackAndWhite(Picture picture) throws IOException {
        BufferedImage image_pixels;
        File f = new File(picture.getPath());
        image_pixels = ImageIO.read(f);
        for (int x = 0; x < image_pixels.getWidth(); ++x)
            for (int y = 0; y < image_pixels.getHeight(); ++y) {
                int rgb = image_pixels.getRGB(x, y);
                int grayLevel = getGrayLevel(rgb);
                int gray = (grayLevel << 16) + (grayLevel << 8) + grayLevel;
                image_pixels.setRGB(x, y, gray);
            }
        return "";
    }

    private static int getGrayLevel(int rgb) {
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = (rgb & 0xFF);

        // Normalize and gamma correct:
        float rr = (float) Math.pow(r / 255.0, 2.2);
        float gg = (float) Math.pow(g / 255.0, 2.2);
        float bb = (float) Math.pow(b / 255.0, 2.2);

        // Calculate luminance:
        float lum = (float) (0.2126 * rr + 0.7152 * gg + 0.0722 * bb);

        // Gamma command and rescale to byte range:
        return (int) (255.0 * Math.pow(lum, 1.0 / 2.2));
    }
}

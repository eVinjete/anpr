package si.evinjete.anpr;

import com.kumuluz.ee.samples.jaxrs.Customer;
import com.kumuluz.ee.samples.jaxrs.Database;
import javaanpr.gui.ReportGenerator;
import javaanpr.imageanalysis.CarSnapshot;
import javaanpr.intelligence.Intelligence;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.nio.file.Files;
import java.util.List;


import javaanpr.gui.ReportGenerator;
import java.io.IOException;
import javaanpr.imageanalysis.CarSnapshot;
import javaanpr.intelligence.Intelligence;

@Path("upload")
public class AnprResource {

    public static ReportGenerator rg = new ReportGenerator();
    public static Intelligence systemLogic;

    @POST
    @Path("/image")
    @Consumes("image/jpeg")
    public Response uploadImage(InputStream uploadedInputStream) throws IOException {

//        byte[] bytes = new byte[0];
//        try (uploadedInputStream) {
//            bytes = uploadedInputStream.readAllBytes();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        File file = File.createTempFile("tablica", ".jpg");
        copyInputStreamToFile(uploadedInputStream, file);
        String absolutePath = file.toString();
        String numberPlate = "";

        System.out.println("Absolute path: " + absolutePath);

        try {
            systemLogic = new Intelligence(false);
            numberPlate = systemLogic.recognize(new CarSnapshot(absolutePath));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Response.status(200).entity(numberPlate).build();
        // return Response.status(200).entity("kurac").build();
    }

    private static void copyInputStreamToFile(InputStream input, File file)
            throws IOException {
        // append = false
        try (OutputStream output = new FileOutputStream(file, false)) {
            input.transferTo(output);
        }
    }
}

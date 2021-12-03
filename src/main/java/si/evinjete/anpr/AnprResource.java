package si.evinjete.anpr;

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
        String pwd = System.getProperty("user.dir");
        File file = File.createTempFile("tablica", ".jpg");
        copyInputStreamToFile(uploadedInputStream, file);
        String absolutePath = file.toString();
        String numberPlate = "";

        try {
            systemLogic = new Intelligence(false, pwd);
            numberPlate = systemLogic.recognize(new CarSnapshot(absolutePath));
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Absolute path: " + absolutePath);
        System.out.println("Number plate: " + numberPlate);

        return Response.status(200).entity(numberPlate).build();
    }

    @POST
    @Path("/slika")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response zaznajSlika(Slika slika) throws IOException {

        if (slika.getContent() == null || slika.getLocation() == null || slika.getTimestamp() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        String pwd = System.getProperty("user.dir");
        File file = File.createTempFile("tablica", ".jpg");
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(slika.getContent());
        String absolutePath = file.toString();
        String numberPlate = "";

        try {
            systemLogic = new Intelligence(false, pwd);
            numberPlate = systemLogic.recognize(new CarSnapshot(absolutePath));
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Absolute path: " + absolutePath);
        System.out.println("Number plate: " + numberPlate);

        return Response.status(200).entity(numberPlate).build();
    }

    private static void copyInputStreamToFile(InputStream input, File file) throws IOException {
        // append = false
        try (OutputStream output = new FileOutputStream(file, false)) {
            input.transferTo(output);
        }
    }
}

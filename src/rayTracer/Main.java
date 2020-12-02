package rayTracer;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

    static int imageWidth = 200;
    static int imageHeight = 200;

    static String fileName = "render.ppm";
    static String path = "C:\\Users\\dldemo\\Desktop\\Renders\\";

    static double focalLength = 1.0;
    static double veiwportHeight = 2.0;
    static double veiwPortWidth = veiwportHeight * (((double)imageWidth) / ((double)imageHeight));

    public static void main(String[] args) {
        //camera
        Vector3 origin = new Vector3(0,0,0);


        //create file
        try {
            File image = new File(path+fileName);
            if (image.createNewFile()) {
                System.out.println("File created: " + image.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        //write To file
        try {
            FileWriter writer = new FileWriter(path+fileName);

            //Header
            writer.write("P3\n"+imageWidth+" "+imageHeight+"\n"+255+"\n");


            for(int y = 0; y < imageHeight; y++){
                for(int x = 0; x < imageWidth; x++){

                    Vector3 pixelColor = new Vector3(x,y,1);

                    writer.write(pixelColor + "\n");
                }
            }

            writer.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }
}

package rayTracer;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

    //Color of rays cast
    static Vector3 rayColor(Ray r, Hittable world) {
        HitRecord record = new HitRecord();
        if(world.hit(r, 0, Double.POSITIVE_INFINITY, record)){
            return record.normal.add(new Vector3(1, 1, 1)).multiply(0.5);
        }

        //Background sky
        Vector3 unitDirection = r.direction.unitVector();
        double t = 0.5*(unitDirection.y + 1.0);
        return (new Vector3(1.0, 1.0, 1.0).multiply(1.0-t)).add(new Vector3(0.5, 0.7, 1.0).multiply(t));
    }


    public static void main(String[] args) {

        //File output
        String fileName = "render.ppm";
        String path = "C:\\Users\\dldemo\\Desktop\\Renders\\";


        //Image
        int imageWidth = 600;
        int imageHeight = 400;

        //world
        HittableList world = new HittableList();
        world.add(new Sphere(new Vector3(0, 0, -1), 0.5));
        world.add(new Sphere(new Vector3(0, -100.5, -1), 100));

        //camera
        double focalLength = 1.0;
        double veiwportHeight = 2.0;
        double veiwPortWidth = veiwportHeight * (((double)imageWidth) / ((double)imageHeight));
        Vector3 origin = new Vector3(0,0,0);
        Vector3 horizontal = new Vector3(veiwPortWidth, 0, 0);
        Vector3 vertical = new Vector3(0, veiwportHeight, 0);
        Vector3 lowerLeftCorner = origin.subtract(horizontal.divide(2))
                                        .subtract(vertical.divide(2))
                                        .subtract(new Vector3(0, 0, focalLength));


        //write To file
        try {
            FileWriter writer = new FileWriter(path+fileName);

            //Header
            writer.write("P3\n"+imageWidth+" "+imageHeight+"\n"+255+"\n");


            //Render image
            for(int y = imageHeight-1; y >= 0; y--){
                for(int x = 0; x < imageWidth; x++){
                    double u = ((double)x) / (imageWidth-1);
                    double v = ((double)y) / (imageHeight-1);

                    Ray r = new Ray(origin, lowerLeftCorner.add(horizontal.multiply(u))
                                                            .add(vertical.multiply(v))
                                                            .subtract(origin));

                    Vector3 pixelColor = rayColor(r, world);

                    writer.write(pixelColor.color() + "\n");
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

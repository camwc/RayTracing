package rayTracer;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

    //Color of rays cast
    static Vector3 rayColor(Ray r, Hittable world, int depth) {
        //Checks depth
        if(depth <= 0){
            return new Vector3(0, 0, 0);
        }


        //Stores running record of hitdata
        HitRecord record = new HitRecord();

        if(world.hit(r, 0.001, Double.POSITIVE_INFINITY, record)){
            Ray scattered = new Ray(new Vector3(0,0,0), new Vector3(0,0,0));
            Vector3 attenuation = new Vector3(0, 0, 0);
            if(record.material.scatter(r, record, attenuation, scattered)){
                return attenuation.multiply(rayColor(scattered, world, depth-1));
            }
            return new Vector3(0,0,0);
            /*
            Vector3 target = record.point.add(record.normal).add(Vector3.randomUnitVector());
            return rayColor(new Ray(record.point, target.subtract(record.point)), world, depth-1).multiply(0.5);
            return record.normal.add(new Vector3(1, 1, 1)).multiply(0.5);

             */
        }

        //Background sky
        Vector3 unitDirection = r.direction.unitVector();
        double t = 0.5*(unitDirection.y + 1.0);
        return (new Vector3(1.0, 1.0, 1.0).multiply(1.0-t)).add(new Vector3(0.5, 0.7, 1.0).multiply(t));
    }

    static String writeColor(Vector3 pixelColor, int samplesPerPixel){
        //Correct for multiple samples
        double scale = 1.0/samplesPerPixel;
        pixelColor = pixelColor.multiply(scale);

        //Correct for gamma
        pixelColor.x = Math.sqrt(pixelColor.x);
        pixelColor.y = Math.sqrt(pixelColor.y);
        pixelColor.z = Math.sqrt(pixelColor.z);
        return pixelColor.color();
    }


    public static void main(String[] args) {

        //File output
        String fileName = "render.ppm";
        String path = "C:\\Users\\dldemo\\Desktop\\Renders\\";


        //Image
        int imageWidth = 600;
        int imageHeight = 400;
        int samplesPerPixel = 100;
        int maxDepth = 50;

        //world
        HittableList world = new HittableList();

        Material mGround = new Lambertian(new Vector3(0.8, 0.8, 0));
        Material mCenter = new Lambertian(new Vector3(0.1, 0.2, 0.5));
        Material mLeft = new Metal(new Vector3(0.8, 0.6, 0.2), 0);
        Material mRight = new Dielectric(1.5);

        world.add(new Sphere(new Vector3(0, -100.5, -1), 100, mGround));
        world.add(new Sphere(new Vector3(0, 0, -1), 0.5, mCenter));
        world.add(new Sphere(new Vector3(-1, 0, -1), 0.5, mLeft));
        world.add(new Sphere(new Vector3(1, 0, -1), 0.5, mRight));

        //camera
        Camera cam = new Camera(imageWidth, imageHeight);

        //write To file
        try {
            FileWriter writer = new FileWriter(path+fileName);

            //Header
            writer.write("P3\n"+imageWidth+" "+imageHeight+"\n"+255+"\n");


            //Render image
            for(int y = imageHeight-1; y >= 0; y--){
                System.out.println("Scanlines remaining: " + y);
                for(int x = 0; x < imageWidth; x++){
                    Vector3 pixelColor = new Vector3(0, 0, 0);
                    for(int s = 0; s < samplesPerPixel; s++){
                        double u = ((double)x+Math.random()) / (imageWidth-1);
                        double v = ((double)y+Math.random()) / (imageHeight-1);

                        Ray r = cam.getRay(u, v);

                        pixelColor = pixelColor.add(rayColor(r, world, maxDepth));
                    }
                    writer.write(writeColor(pixelColor, samplesPerPixel) + "\n");
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

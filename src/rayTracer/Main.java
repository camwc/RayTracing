package rayTracer;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


//TIME TO BEAT 18.02 seconds AVG
public class Main {

    //Color of rays cast
    static Vector3 rayColor(Ray r, Hittable world, int depth) {
        //Checks bounce depth
        if(depth <= 0){
            return new Vector3(0, 0, 0);
        }

        //Stores running record of hitdata
        HitRecord record = new HitRecord();

        if(world.hit(r, 0.001, Double.POSITIVE_INFINITY, record)){
            Ray scattered = new Ray(new Vector3(0,0,0), new Vector3(0,0,0));
            Vector3 attenuation = new Vector3(0, 0, 0);

            if(record.material.scatter(r, record, attenuation, scattered)){
                //Recursively bounces ray
                return attenuation.multiply(rayColor(scattered, world, depth-1));
            }

            //Returns black if Ray absorbed
            return new Vector3(0,0,0);
        }

        //Background sky
        Vector3 unitDirection = r.direction.unitVector();
        double t = 0.5*(unitDirection.y + 1.0);
        return (new Vector3(1.0, 1.0, 1.0).multiply(1.0-t)).add(new Vector3(0.5, 0.7, 1.0).multiply(t));
    }

    static Vector3 adjustColor(Vector3 pixelColor, int samplesPerPixel){
        //Correct for multiple samples
        double scale = 1.0/samplesPerPixel;
        pixelColor = pixelColor.multiply(scale);

        //Correct for gamma
        pixelColor.x = Math.sqrt(pixelColor.x);
        pixelColor.y = Math.sqrt(pixelColor.y);
        pixelColor.z = Math.sqrt(pixelColor.z);
        return pixelColor;
    }

    static void writePPM(Vector3[] pixelArray, int imageHeight,int imageWidth, String path, String fileName) throws ArrayIndexOutOfBoundsException{
        if(pixelArray.length != imageHeight*imageWidth){
            throw new ArrayIndexOutOfBoundsException("pixel Array is not the correct length for the given resolution");
        }

        try {
            FileWriter writer = new FileWriter(path+fileName);

            //Header
            writer.write("P3\n"+imageWidth+" "+imageHeight+"\n"+255+"\n");

            for(int i = 0; i < pixelArray.length; i++){
                writer.write(pixelArray[i].color() + "\n");
            }

            writer.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

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
        Vector3 cameraPos = new Vector3(2,2,1);
        Vector3 lookAt = new Vector3(0, 0, -1);
        Vector3 up = new Vector3(0, 1, 0);
        double distFocus = cameraPos.subtract(lookAt).length();
        double aperture = 0;

        Camera cam = new Camera(cameraPos, lookAt, up, 30, imageWidth, imageHeight, aperture, distFocus);

        //render image
        Vector3[] pixelArray = new Vector3[imageWidth*imageHeight];

        int numOfThreads = 1;

        int availableProcessors = Runtime.getRuntime().availableProcessors();
        numOfThreads = Math.min(numOfThreads, availableProcessors);

        Thread[] threads = new Thread[numOfThreads];

        Long startTime = System.currentTimeMillis();

        int stepSize = imageHeight/numOfThreads;
        int remainder = imageHeight%numOfThreads;
        for(int i = 0; i < numOfThreads; i++){

            int startScanLine = imageHeight - stepSize*i;
            int endScanLine = startScanLine - stepSize;
            if(i == numOfThreads - 1){
                endScanLine -= remainder;
            }

            Renderer r = new Renderer(pixelArray, cam, world, imageHeight, imageWidth, samplesPerPixel, maxDepth, startScanLine, endScanLine);
            threads[i] = new Thread(r, ""+i);

            threads[i].start();
        }

        for(int i = 0; i < numOfThreads; i++){
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        /*
        Vector3[] pixelArray = new Vector3[imageWidth*imageHeight];
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

                pixelArray[imageWidth*(-y+imageHeight-1)+x] = adjustColor(pixelColor, samplesPerPixel);
            }
        }
        */



        //write To file
        writePPM(pixelArray, imageHeight, imageWidth, path, fileName);

        System.out.println("Rendered in " + (System.currentTimeMillis()-startTime) + " milliseconds");

    }
}

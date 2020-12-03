package rayTracer;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

    static int imageWidth = 600;
    static int imageHeight = 400;

    static String fileName = "render.ppm";
    static String path = "C:\\Users\\dldemo\\Desktop\\Renders\\";

    static double focalLength = 1.0;
    static double veiwportHeight = 2.0;
    static double veiwPortWidth = veiwportHeight * (((double)imageWidth) / ((double)imageHeight));

    //returns ray length of sphere intersection
    static double hitSphere(Vector3 center, double radius, Ray r){
        Vector3 oc = r.origin.subtract(center);
        double a = r.direction.dot(r.direction);
        double b = 2.0 * oc.dot(r.direction);
        double c = oc.dot(oc) - radius*radius;
        double discriminant = b*b - 4*a*c;
        if (discriminant < 0) {
            return -1.0;
        } else {
            return (-b - Math.sqrt(discriminant) ) / (2.0*a);
        }
    }

    //Color of rays cast
    static Vector3 rayColor(Ray r) {
        double t = hitSphere(new Vector3(0, 0, -1),0.5, r);
        if(t>0){
            Vector3 normal = r.at(t).subtract(new Vector3(0, 0, -1)).unitVector();
            return new Vector3(normal.x+1, normal.y+1, normal.z+1).multiply(0.5);
        }


        //Background sky
        Vector3 unitDirection = r.direction.unitVector();
        t = 0.5*(unitDirection.y + 1.0);
        return (new Vector3(1.0, 1.0, 1.0).multiply(1.0-t)).add(new Vector3(0.5, 0.7, 1.0).multiply(t));
    }


    public static void main(String[] args) {
        //camera
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

                    Vector3 pixelColor = rayColor(r);

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

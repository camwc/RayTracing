package rayTracer;

public class Camera {
    double focalLength = 1.0;
    double veiwportHeight = 2.0;
    double veiwPortWidth;

    Vector3 origin;
    Vector3 horizontal;
    Vector3 vertical;
    Vector3 lowerLeftCorner;

    public Camera(int imageWidth, int imageHeight){
        veiwPortWidth = veiwportHeight * (((double)imageWidth) / ((double)imageHeight));
        origin = new Vector3(0,0,0);
        horizontal = new Vector3(veiwPortWidth, 0, 0);
        vertical = new Vector3(0, veiwportHeight, 0);
        lowerLeftCorner = origin.subtract(horizontal.divide(2))
                .subtract(vertical.divide(2))
                .subtract(new Vector3(0, 0, focalLength));
    }

    public Ray getRay(double u, double v){
        return new Ray(origin, lowerLeftCorner.add(horizontal.multiply(u))
                .add(vertical.multiply(v))
                .subtract(origin));
    }

}

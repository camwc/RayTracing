package rayTracer;

public class Camera {
    double viewPortHeight;
    double viewPortWidth;

    Vector3 origin;
    Vector3 lookAt;
    Vector3 vUp;

    double lensRadius;
    double focusDist;

    Vector3 horizontal;
    Vector3 vertical;
    Vector3 lowerLeftCorner;

    Vector3 u,v,w;

    public Camera(Vector3 lookFrom, Vector3 lookAt, Vector3 vUp, double vFov, int imageWidth, int imageHeight, double aperture, double focusDist){
        this(lookFrom, lookAt, vUp, vFov, ((double)imageWidth) / ((double)imageHeight),  aperture,  focusDist);
    }

    public Camera(Vector3 lookFrom, Vector3 lookAt, Vector3 vUp, double vFov, double aspectRatio, double aperture, double focusDist){

        this.lookAt = lookAt;
        this.vUp = vUp;
        this.lensRadius = aperture/2;
        this.focusDist = focusDist;

        double theta = Math.toRadians(vFov);
        double h = Math.tan(theta/2);
        viewPortHeight = 2.0 * h;
        viewPortWidth = viewPortHeight * aspectRatio;


        w = lookFrom.subtract(lookAt).unitVector();
        u = vUp.cross(w).unitVector();
        v = w.cross(u);

        origin = lookFrom;
        horizontal = u.multiply(viewPortWidth*focusDist);
        vertical = v.multiply(viewPortHeight*focusDist);
        lowerLeftCorner = origin.subtract(horizontal.divide(2))
                .subtract(vertical.divide(2))
                .subtract(w.multiply(focusDist));
    }

    public Ray getRay(double s, double t){
        Vector3 rd = Vector3.randomInUnitDisc().multiply(lensRadius);
        Vector3 offset = u.multiply(rd.x).add(v.multiply(rd.y));

        return new Ray(origin.add(offset), lowerLeftCorner.add(horizontal.multiply(s))
                .add(vertical.multiply(t))
                .subtract(origin).subtract(offset));
    }

}

package rayTracer;

public class HitRecord{
    Vector3 point;
    Vector3 normal;
    double t;
    boolean frontFace;
    Material material;

    public HitRecord(){};

    public HitRecord(Vector3 point, Vector3 normal, double t) {
        this.point = point;
        this.normal = normal;
        this.t = t;
    }

    void setFaceNormal(Ray r, Vector3 outwardNormal){
        frontFace = r.direction.dot(outwardNormal) < 0;
        normal = frontFace ? outwardNormal : outwardNormal.multiply(-1);
    }

}
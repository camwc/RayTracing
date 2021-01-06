package rayTracer;

public class HitRecord{
    Vector3 point;
    Vector3 normal;
    double t;
    double u;
    double v;
    boolean frontFace;
    Material material;

    public HitRecord(){};

    public HitRecord(Vector3 point, Vector3 normal, double t) {
        this.point = point;
        this.normal = normal;
        this.t = t;

        //default uv to 0
        //overwritten by using getUV in the hittable's hit() function
        this.u = 0;
        this.v = 0;
    }

    void setFaceNormal(Ray r, Vector3 outwardNormal){
        frontFace = r.direction.dot(outwardNormal) < 0;
        normal = frontFace ? outwardNormal : outwardNormal.multiply(-1);
    }

}
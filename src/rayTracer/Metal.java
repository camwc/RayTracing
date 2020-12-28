package rayTracer;

public class Metal extends Material{
    Vector3 albedo;
    double fuzz;

    public Metal(Vector3 albedo, double fuzz){
        this.albedo = albedo;
        this.fuzz = (fuzz < 1) ? fuzz : 1;
    }

    @Override
    boolean scatter(Ray rIn, HitRecord record, Vector3 attenuation, Ray scattered) {
        Vector3 reflected = rIn.direction.unitVector().reflect(record.normal);

        scattered.changeValues(record.point, reflected.add(Vector3.randomInUnitSphere().multiply(fuzz)));
        attenuation.changeValues(albedo);
        return scattered.direction.dot(record.normal) > 0;
    }
}

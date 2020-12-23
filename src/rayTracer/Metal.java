package rayTracer;

public class Metal extends Material{
    Vector3 albedo;

    public Metal(Vector3 albedo){
        this.albedo = albedo;
    }

    @Override
    boolean scatter(Ray rIn, HitRecord record, Vector3 attenuation, Ray scattered) {
        Vector3 reflected = rIn.direction.unitVector().reflect(record.normal);

        scattered.changeValues(record.point, reflected);
        attenuation.changeValues(albedo);
        return scattered.direction.dot(record.normal) > 0;
    }
}

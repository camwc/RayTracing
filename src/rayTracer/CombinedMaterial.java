package rayTracer;

//Experimental class
public class CombinedMaterial extends Material {
    Vector3 albedo;
    double indexOfRefraction;
    double opacity;
    double fuzz;
    private Lambertian l;
    private Dielectric d;
    private Metal m;

    public CombinedMaterial(Vector3 albedo, double indexOfRefraction, double opacity, double fuzz) {
        this.albedo = albedo;
        this.indexOfRefraction = indexOfRefraction;
        this.opacity = opacity;
        this.fuzz = fuzz;

        l = new Lambertian(albedo);
        m = new Metal(albedo, fuzz);
        d = new Dielectric(albedo, indexOfRefraction);
    }

    @Override
    boolean scatter(Ray rIn, HitRecord record, Vector3 attenuation, Ray scattered) {
        if(Math.random() > opacity){
            return d.scatter(rIn, record, attenuation, scattered);
        }
        else if(fuzz <= 1){
            return m.scatter(rIn, record, attenuation, scattered);
        }

        return l.scatter(rIn, record, attenuation, scattered);
    }
}

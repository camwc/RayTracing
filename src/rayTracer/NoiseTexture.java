package rayTracer;

public class NoiseTexture extends Texture{
    public Perlin noise;
    public double scale;

    public NoiseTexture(){
        this.noise = new Perlin();
        this.scale = 1;
    }

    public NoiseTexture(double scale){
        this.noise = new Perlin();
        this.scale = scale;
    }

    @Override
    public Vector3 colorValue(double u, double v, Vector3 p) {
        return new Vector3(1,1,1).multiply(1 + noise.noise(p.multiply(scale))).multiply(0.5);
    }
}

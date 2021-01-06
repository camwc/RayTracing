package rayTracer;

public class CheckerTexture extends Texture{

    private Texture even, odd;
    double scale;

    public CheckerTexture(Texture even, Texture odd) {
        this.even = even;
        this.odd = odd;
        this.scale = 10;
    }

    public CheckerTexture(Vector3 color1, Vector3 color2){
        this.even = new SolidColor(color1);
        this.odd = new SolidColor(color2);
        this.scale = 10;
    }

    public CheckerTexture(Texture even, Texture odd, double scale) {
        this.even = even;
        this.odd = odd;
        this.scale = scale;
    }

    public CheckerTexture(Vector3 color1, Vector3 color2, double scale){
        this.even = new SolidColor(color1);
        this.odd = new SolidColor(color2);
        this.scale = scale;
    }

    @Override
    public Vector3 colorValue(double u, double v, Vector3 p) {
        double sines = Math.sin(scale*p.x)*Math.sin(scale*p.y)*Math.sin(scale*p.z);
        if(sines < 0){
            return odd.colorValue(u, v, p);
        }
        return even.colorValue(u, v, p);
    }
}

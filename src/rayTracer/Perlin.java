package rayTracer;

//I do not understand any of this

public class Perlin {
    private static int pointCount = 256;
    private double ranFloat[];
    private int[] permX, permY, permZ;

    public Perlin(){
        ranFloat = new double[pointCount];
        for(int i = 0; i < pointCount; i++){
            ranFloat[i] = Math.random();
        }

        permX = perlinGeneratePerm();
        permY = perlinGeneratePerm();
        permZ = perlinGeneratePerm();

    }

    double noise(Vector3 point) {
        double u = point.x - Math.floor(point.x);
        double v = point.y - Math.floor(point.y);
        double w = point.z - Math.floor(point.z);
        u = u*u*(3-2*u);
        v = v*v*(3-2*v);
        w = w*w*(3-2*w);

        int i = (int) (4 * point.x) & 255;
        int j = (int) (4 * point.y) & 255;
        int k = (int) (4 * point.z) & 255;

        double[][][] c = new double[2][2][2];

        for (int di = 0; di < 2; di++) {
            for (int dj = 0; dj < 2; dj++) {
                for (int dk = 0; dk < 2; dk++) {
                    c[di][dj][dk] = ranFloat[permX[(i + di) & 255] ^ permY[(j + dj) & 255] ^ permZ[(k + dk) & 255]];
                }
            }
        }

        return trilinearInterp(c, u, v, w);
    }

    private static int[] perlinGeneratePerm(){
        int[] p = new int[pointCount];

        for(int i = 0; i < pointCount; i++){
            p[i] = i;
        }

        permute(p, pointCount);

        return p;
    }

    private static void permute(int[] p, int n){
        for(int i = n -1; i > 0; i--){
            int target = (int)(Math.random()*i) + 1;
            int tmp = p[i];
            p[i] = p[target];
            p[target] = tmp;

        }
    }

    //Wat
    private static double trilinearInterp(double c[][][], double u, double v, double w){
        double accum = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    accum += (i * u + (1 - i) * (1 - u)) *
                            (j * v + (1 - j) * (1 - v)) *
                            (k * w + (1 - k) * (1 - w)) * c[i][j][k];
                }
            }
        }
        return accum;
    }



}

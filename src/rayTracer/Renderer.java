package rayTracer;

public class Renderer implements Runnable {

    Vector3[] pixelArray;
    int imageHeight;
    int imageWidth;
    Camera cam;
    Hittable world;
    int samplesPerPixel;
    int maxDepth;
    int startScanLine;
    int endScanLine;

    public Renderer(Vector3[] pixelArray, Camera cam, Hittable world, int imageHeight, int imageWidth, int samplesPerPixel, int maxDepth, int startScanLine, int endScanLine) {
        this.pixelArray = pixelArray;
        this.cam = cam;
        this.world = world;
        this.imageHeight = imageHeight;
        this.imageWidth = imageWidth;
        this.samplesPerPixel = samplesPerPixel;
        this.maxDepth = maxDepth;
        this.startScanLine = startScanLine;
        this.endScanLine = endScanLine;
    }


    @Override
    public void run() {
        for(int y = startScanLine-1; y >= endScanLine; y--){
            System.out.println("Scanlines remaining: " + y);
            for(int x = 0; x < imageWidth; x++){
                Vector3 pixelColor = new Vector3(0, 0, 0);
                for(int s = 0; s < samplesPerPixel; s++){
                    double u = ((double)x+Math.random()) / (imageWidth-1);
                    double v = ((double)y+Math.random()) / (imageHeight-1);

                    Ray r = cam.getRay(u, v);

                    pixelColor = pixelColor.add(Main.rayColor(r, world, maxDepth));
                }

                pixelArray[imageWidth*(-y+imageHeight-1)+x] = Main.adjustColor(pixelColor, samplesPerPixel);
            }
        }
    }
}

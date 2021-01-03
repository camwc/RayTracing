package rayTracer;

import java.util.ArrayList;

public class HittableList extends Hittable {

    ArrayList<Hittable> objects;

    HittableList(){
        objects = new ArrayList<Hittable>();
    }

    HittableList(Hittable object){
        this.add(object);
    }

    void add(Hittable object){
        objects.add(object);
    }


    @Override
    boolean hit(Ray r, double tMin, double tMax, HitRecord record) {
        boolean hitAnything = false;
        double closestSoFar = tMax;

        for(Hittable o : objects){
            if(o.hit(r, tMin, closestSoFar, record)){
                hitAnything = true;
                closestSoFar = record.t;
                record = record;
            }
        }

        return hitAnything;
    }

    @Override
    boolean boundingBox(AABB outputBox) {
        if(objects.isEmpty()){
            return false;
        }

        AABB tempBox = new AABB();
        AABB outputTemp = outputBox;
        boolean firstBox = true;

        for(Hittable object : objects){
            if(!object.boundingBox(tempBox)){
                return false;
            }
            outputTemp = firstBox ? tempBox : AABB.surroundingBox(outputTemp, tempBox);
            firstBox = false;
        }
        outputBox.changeValues(outputTemp);

        return true;
    }
}

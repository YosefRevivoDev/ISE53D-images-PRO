package lighting;

import primitives.Color;
import primitives.Double3;


public class AmbientLight extends Light {

    /**
     * c'tor default
     */
    public AmbientLight() {
       super(Color.BLACK) ;
    }

    /**
     * c'tor for ambient light intensity
     * @param Ia intensity light
     * @param Ka מקדם הנחתה
     */
    public AmbientLight(Color Ia, Double3 Ka) {
        super(Ia.scale(Ka)) ;
    }
}

package lighting;

import primitives.Color;
import primitives.Double3;


public class AmbientLight  {

    private Color intensity;

    /**
     * c'tor default
     */
    public AmbientLight(){
       this.intensity = Color.BLACK;
    }

    /**
     * c'tor for ambient light intensity
     * @param Ia intensity light
     * @param Ka מקדם הנחתה
     */
    public AmbientLight(Color Ia, Double3 Ka) {
        this.intensity = Ia.scale(Ka);
    }

    /**
     * get intensity
     * @return
     */
    public Color getIntensity(){
        return (intensity);
    }
}

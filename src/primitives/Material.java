package primitives;

/**
 * PDS class
 * no funcitons only public fields
 */
public class Material
{
    // all the fieds have a default value of 0, there is a default constructor
    public Double3 kD = new Double3(0.0), kS = new Double3(0.0);
    public Double3 kT = new Double3(0.0), kR = new Double3(0.0);
    public int nShininess=0;

    /**
     * setter according to the builder pattern
     * @param kD
     * @return
     */
    public Material setkD(Double3 kD) {
        this.kD = kD;
        return this;
    }

    public Material setkD(double kD) {
        this.kD = new Double3(kD);
        return this;
    }
    /**
     * setter according to the builder pattern
     * @param kS
     * @return
     */
    public Material setkS(Double3 kS) {
        this.kS = kS;
        return this;
    }

    public Material setkS(double kS) {
        this.kS = new Double3(kS);
        return this;
    }

    /**
     * setter according to the builder pattern
     *
     * @param kt
     * @return
     */
    public Material setKt(Double3 kt) {
        this.kT = kt;
        return this;
    }

    /**
     * setter according to the builder pattern
     *
     * @param kt
     * @return
     */
    public Material setKt(Double kt) {
        kT = new Double3(kt);
        return this;
    }

    /**
     * setter according to the builder pattern
     *
     * @param kr
     * @return
     */
    public Material setKr(Double3 kr) {
        kR = kr;
        return this;
    }

    /**
     * setter according to the builder pattern
     *
     * @param kr
     * @return
     */
    public Material setKr(Double kr) {
        kR = new Double3(kr);
        return this;
    }


    /**
     * setter according to the builder pattern
     * @param nShininess
     * @return
     */
    public Material setnShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }
}
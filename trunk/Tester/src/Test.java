/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Victor
 */
public class Test {
    private int idTest;
    private int it;
    private int deltaGrados;
    private float gradosMotores;
    private float tiempoTacto;
    private float tiempoUltrasonido;
    private float tiempoLuz;

    public Test(int idTest, int it, int deltaGrados, float gradosMotores, float tiempoTacto, float tiempoUltrasonido, float tiempoLuz) {
        this.idTest = idTest;
        this.it = it;
        this.deltaGrados = deltaGrados;
        this.gradosMotores = gradosMotores;
        this.tiempoTacto = tiempoTacto;
        this.tiempoUltrasonido = tiempoUltrasonido;
        this.tiempoLuz = tiempoLuz;
    }

    public Test(int idTest, int it, int deltaGrados, float gradosMotores) {
        this.idTest = idTest;
        this.it = it;
        this.deltaGrados = deltaGrados;
        this.gradosMotores = gradosMotores;
    }

    public Test(int idTest, int it, int deltaGrados) {
        this.idTest = idTest;
        this.it = it;
        this.deltaGrados = deltaGrados;
    }


    public int getDeltaGrados() {
        return deltaGrados;
    }

    public void setDeltaGrados(int deltaGrados) {
        this.deltaGrados = deltaGrados;
    }

    public float getGradosMotores() {
        return gradosMotores;
    }

    public void setGradosMotores(float gradosMotores) {
        this.gradosMotores = gradosMotores;
    }

    public int getIdTest() {
        return idTest;
    }

    public void setIdTest(int idTest) {
        this.idTest = idTest;
    }

    public int getIt() {
        return it;
    }

    public void setIt(int it) {
        this.it = it;
    }

    public float getTiempoLuz() {
        return tiempoLuz;
    }

    public void setTiempoLuz(float tiempoLuz) {
        this.tiempoLuz = tiempoLuz;
    }

    public float getTiempoTacto() {
        return tiempoTacto;
    }

    public void setTiempoTacto(float tiempoTacto) {
        this.tiempoTacto = tiempoTacto;
    }

    public float getTiempoUltrasonido() {
        return tiempoUltrasonido;
    }

    public void setTiempoUltrasonido(float tiempoUltrasonido) {
        this.tiempoUltrasonido = tiempoUltrasonido;
    }

    public String toString(){
    	return (
        "idTest" + idTest +
        "it" + it +
        "deltaGrados" + deltaGrados +
        "gradosMotores" + gradosMotores +
        "tiempoTacto" + tiempoTacto +
        "tiempoUltrasonido" + tiempoUltrasonido +
        "tiempoLuz" + tiempoLuz);
    }


}

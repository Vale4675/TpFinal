package Sistema;

public class Cuota {
    private int comprobante;
    private static int contadorComprobante = 1;
    private double importe;
    private boolean pagado;

    public Cuota(int comprobante, double importe, boolean pagado) {
        this.comprobante = comprobante;
        this.importe = importe;
        this.pagado = pagado;
    }

    public int getComprobante() {
        return comprobante;
    }

    public double getImporte() {
        return importe;
    }

    public boolean isPagado() {
        return pagado;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public void setPagado(boolean pagado) {
        this.pagado = pagado;
    }

    public void setComprobante(int comprobante) {
        this.comprobante = comprobante;
    }

    public static void setContadorComprobante(int contadorComprobante) {
        Cuota.contadorComprobante = contadorComprobante;
    }

    @Override
    public String toString() {
        return "Cuota{" +
                "comprobante='" + comprobante + '\'' +
                ", importe=" + importe +
                ", pagado=" + pagado +
                '}';
    }
}

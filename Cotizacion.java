public class Cotizacion {
    private String planFinanciamiento;
    private String personalidadFiscal;
    private String enganche;
    private String plazo;
    private String aseguradora;
    private String tipoPago;

    public Cotizacion(String planFinanciamiento, String personalidadFiscal, String enganche, String plazo, String aseguradora, String tipoPago) {

        this.planFinanciamiento = planFinanciamiento;
        this.personalidadFiscal = personalidadFiscal;
        this.enganche = enganche;
        this.plazo = plazo;
        this.aseguradora = aseguradora;
        this.tipoPago = tipoPago;
    }

    public void setPlanFinanciamiento(String planFinanciamiento) {
        this.planFinanciamiento = planFinanciamiento;
    }
    public void setPersonalidadFiscal(String personalidadFiscal) {
        this.personalidadFiscal = personalidadFiscal;
    }
    public void setEnganche(String enganche) {
        this.enganche = enganche;
    }
    public void setPlazo(String plazo) {
        this.plazo = plazo;
    }
    public void serAseguradora(String aseguradora) {
        this.aseguradora = aseguradora;
    }
    public void setTipoPago(String tipoPago) {
        this.tipoPago = tipoPago;
    }

    public String getPlanFinanciamiento() {
        return this.planFinanciamiento;
    }
    public String getPersonalidadFiscal() {
        return this.personalidadFiscal;
    }
    public String getEnganche() {
        return this.enganche;
    }
    public String getPlazo() {
        return this.plazo;
    }
    public String getAseguradora() {
        return this.aseguradora;
    }
    public String getTipoPago() {
        return this.tipoPago;
    }
}
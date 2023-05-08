public class Cliente {
    private String nombre;
    private String paterno;
    private String materno;
    private Ubicacion ubicacion;
    private String correo;
    private String celular;

    public Cliente(String nombre, String paterno, String materno, Ubicacion ubicacion, String correo, String celular) {
        this.setNombre(nombre);
        this.setPaterno(paterno);
        this.setMaterno(materno);
        this.setUbicacion(ubicacion);
        this.setCorreo(correo);
        this.setCelular(celular);
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setPaterno(String paterno) {
        this.paterno = paterno;
    }
    public void setMaterno(String materno) {
        this.materno = materno;
    }
    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getNombre() {
        return this.nombre;
    }
    public String getPaterno() {
        return this.paterno;
    }
    public String getMaterno() {
        return this.materno;
    }
    public Ubicacion getUbicacion() {
        return this.ubicacion;
    }
    public String getCorreo() {
        return this.correo;
    }
    public String getCelular() {
        return this.celular;
    }
}

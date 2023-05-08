

public class Autos {
    private String uso;
    private String version;
    private Integer modelo;
    private String color;

    public Autos(String uso, String version, Integer modelo, String color) {
        this.setUso(uso);;
        this.setVersion(version);;
        this.setModelo(modelo);;
        this.setColor(color);;
    }

    public void setUso(String uso) {
        this.uso = uso;
    }
    public void setVersion(String version) {
        this.version = version;
    }
    public void setModelo(Integer modelo) {
        this.modelo = modelo;
    }
    public void setColor(String color) {
        this.color = color;
    }

    public String getUso() {
        return this.uso;
    }
    public String getVersion() {
        return this.version;
    }
    public Integer getModelo() {
        return this.modelo;
    }
    public String getColor() {
        return this.color;
    }
}

public class Administrador {
    private int idAdministrador;
    private String nombre;
    private String email;
    private String password;

    public Administrador(int idAdministrador, String nombre, String email, String password) {
        this.idAdministrador = idAdministrador;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
    }

    public int getIdAdministrador() {
        return idAdministrador;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Administrador{" +
                "idAdministrador=" + idAdministrador +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
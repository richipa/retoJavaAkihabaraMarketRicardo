package modelo;

import java.time.LocalDate;

public class ClienteOtaku {
    private int id;
    private String nombre;
    private String email;
    private String telefono;
    private LocalDate fechaRegistro;

    public ClienteOtaku(int id, String nombre, String email, String telefono, LocalDate fechaRegistro) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.fechaRegistro = LocalDate.now();
    }

    public ClienteOtaku(String nombre, String email, String telefono) {
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.fechaRegistro = LocalDate.now();
    }

    // Getters y Setters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
    public String getTelefono() { return telefono; }
    public LocalDate getFechaRegistro() { return fechaRegistro; }

    public void setId(int id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setEmail(String email) { this.email = email; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public void setFechaRegistro(LocalDate fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}

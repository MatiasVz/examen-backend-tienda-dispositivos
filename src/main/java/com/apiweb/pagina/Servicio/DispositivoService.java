package com.apiweb.pagina.Servicio;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.apiweb.pagina.DTOs.DispositivoDTO;
import com.apiweb.pagina.Entidades.Dispositivo;
import com.apiweb.pagina.Entidades.DispositivoImagen;
import com.apiweb.pagina.Repositorios.DispositivoRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DispositivoService {

    private final DispositivoRepository dispositivoRepository;
    @Autowired
    private Environment env;

    @Transactional
    public Dispositivo saveFullDispositivo(Dispositivo dispositivo) {
        Dispositivo disp = new Dispositivo();
        disp.setNombre(dispositivo.getNombre());
        disp.setCategoria(dispositivo.getCategoria());
        disp.setMarca(dispositivo.getMarca());
        disp.setModelo(dispositivo.getModelo());
        disp.setDescripcion(dispositivo.getDescripcion());
        disp.setPrecio(dispositivo.getPrecio());
        disp.setStock(dispositivo.getStock());
        disp.setEstado(1);

        List<DispositivoImagen> imagenes = new ArrayList<>();

        if (dispositivo.getImagenes() != null) {
            for (DispositivoImagen imgDto : dispositivo.getImagenes()) {
                DispositivoImagen imagen = new DispositivoImagen();
                imagen.setSecuencial(null);
                String url = imgDto.getUrl();
                if (url != null && url.startsWith("data:image/")) {
                    // Es base64
                    try {
                        String filePath = guardarImagenBase64EnServidor(url);
                        imagen.setUrl(filePath); // Guarda solo la ruta relativa o completa
                    } catch (IOException e) {
                        e.printStackTrace();
                        continue; // Saltar si hay error
                    }
                } else {
                    // Es una URL externa o local ya válida
                    imagen.setUrl(url);
                }

                imagen.setDispositivo(disp); // relación inversa
                imagenes.add(imagen);
            }
        }

        disp.setImagenes(imagenes);
        return dispositivoRepository.save(disp);
    }

    @Transactional
    public Optional<Dispositivo> updateDispositivo(Long id, Dispositivo dispositivoActualizado) {
        return dispositivoRepository.findById(id).map(dispositivoExistente -> {
            dispositivoExistente.setNombre(dispositivoActualizado.getNombre());
            dispositivoExistente.setCategoria(dispositivoActualizado.getCategoria());
            dispositivoExistente.setMarca(dispositivoActualizado.getMarca());
            dispositivoExistente.setModelo(dispositivoActualizado.getModelo());
            dispositivoExistente.setDescripcion(dispositivoActualizado.getDescripcion());
            dispositivoExistente.setPrecio(dispositivoActualizado.getPrecio());
            dispositivoExistente.setStock(dispositivoActualizado.getStock());
            dispositivoExistente.setEstado(dispositivoActualizado.getEstado());

            List<DispositivoImagen> listaActual = dispositivoExistente.getImagenes();
            listaActual.clear();
            if (dispositivoActualizado.getImagenes() != null) {
                for (DispositivoImagen imagenNueva : dispositivoActualizado.getImagenes()) {
                    DispositivoImagen imagen = new DispositivoImagen();
                    if (imagenNueva.getUrl().startsWith("data:image/")) {
                        try {
                            String filePath = guardarImagenBase64EnServidor(imagenNueva.getUrl());
                            imagen.setUrl(filePath);
                        } catch (IOException e) {
                            e.printStackTrace();
                            continue;
                        }
                    } else {
                        imagen.setUrl(imagenNueva.getUrl());
                    }
                    imagen.setDispositivo(dispositivoExistente);
                    listaActual.add(imagen);
                }
            }
            return dispositivoRepository.save(dispositivoExistente);
        });
    }

    @Transactional
    public Dispositivo saveFromDTO(DispositivoDTO dto) {
        Dispositivo dispositivo = new Dispositivo();
        dispositivo.setNombre(dto.getNombre());
        dispositivo.setCategoria(dto.getCategoria());
        dispositivo.setMarca(dto.getMarca());
        dispositivo.setModelo(dto.getModelo());
        dispositivo.setDescripcion(dto.getDescripcion());
        dispositivo.setPrecio(dto.getPrecio());
        dispositivo.setStock(dto.getStock());
        dispositivo.setEstado(dto.getEstado() != null ? dto.getEstado() : 1);
        List<DispositivoImagen> imagenes = new ArrayList<>();
        if (dto.getImagenes() != null) {
            for (String url : dto.getImagenes()) {
                DispositivoImagen imagen = new DispositivoImagen();
                imagen.setSecuencial(null);
                if (url != null && url.startsWith("data:image/")) {
                    try {
                        String filePath = guardarImagenBase64EnServidor(url);
                        imagen.setUrl(filePath);
                    } catch (IOException e) {
                        e.printStackTrace();
                        continue;
                    }
                } else {
                    imagen.setUrl(url);
                }
                imagen.setDispositivo(dispositivo);
                imagenes.add(imagen);
            }
        }
        dispositivo.setImagenes(imagenes);
        return dispositivoRepository.save(dispositivo);
    }

    @Transactional
    public Optional<Dispositivo> updateFromDTO(Long id, DispositivoDTO dto) {
        return dispositivoRepository.findById(id).map(dispositivoExistente -> {
            dispositivoExistente.setNombre(dto.getNombre());
            dispositivoExistente.setCategoria(dto.getCategoria());
            dispositivoExistente.setMarca(dto.getMarca());
            dispositivoExistente.setModelo(dto.getModelo());
            dispositivoExistente.setDescripcion(dto.getDescripcion());
            dispositivoExistente.setPrecio(dto.getPrecio());
            dispositivoExistente.setStock(dto.getStock());
            dispositivoExistente.setEstado(dto.getEstado() != null ? dto.getEstado() : 1);
            List<DispositivoImagen> listaActual = dispositivoExistente.getImagenes();
            listaActual.clear();
            if (dto.getImagenes() != null) {
                for (String url : dto.getImagenes()) {
                    DispositivoImagen imagen = new DispositivoImagen();
                    if (url != null && url.startsWith("data:image/")) {
                        try {
                            String filePath = guardarImagenBase64EnServidor(url);
                            imagen.setUrl(filePath);
                        } catch (IOException e) {
                            e.printStackTrace();
                            continue;
                        }
                    } else {
                        imagen.setUrl(url);
                    }
                    imagen.setDispositivo(dispositivoExistente);
                    listaActual.add(imagen);
                }
            }
            return dispositivoRepository.save(dispositivoExistente);
        });
    }

    public String guardarImagenBase64EnServidor(String base64) throws IOException {
        // Directorio donde se guardarán las imágenes
        String directorioDestino = "C:/imagenes/dispositivos/";
        
        // Asegurarse de que el directorio existe
        File directorio = new File(directorioDestino);
        if (!directorio.exists()) {
            directorio.mkdirs(); // crear si no existe
        }

        // Separar el encabezado del base64 (ej: data:image/png;base64,...)
        String[] partes = base64.split(",");
        if (partes.length != 2) {
            throw new IllegalArgumentException("Formato base64 inválido");
        }

        String base64Data = partes[1];
        byte[] imagenBytes = Base64.getDecoder().decode(base64Data);

        // Crear nombre único para la imagen
        String nombreArchivo = "imagen_" + System.currentTimeMillis() + ".png";
        String rutaCompleta = directorioDestino + nombreArchivo;

        // Guardar archivo
        try (FileOutputStream fos = new FileOutputStream(rutaCompleta)) {
            fos.write(imagenBytes);
        }

        // Devolver ruta relativa para la base de datos
        String baseUrl = env.getProperty("servidorImagens"); // "http://localhost:8082"
        String urlCompleta = baseUrl + "/imagenes/dispositivos/" + nombreArchivo;
        return urlCompleta;
    }

    public List<Dispositivo> listAll() {
        return dispositivoRepository.findAll();
    }

    public Optional<Dispositivo> findById(Long id) {
        return dispositivoRepository.findById(id);
    }

    @Transactional
    public boolean deleteById(Long id) {
        if (dispositivoRepository.existsById(id)) {
            dispositivoRepository.deleteById(id);
            return true;
        }
        return false;
    }
} 
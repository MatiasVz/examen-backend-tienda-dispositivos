package com.apiweb.pagina.DTOs;

import lombok.Data;

@Data
public class BannerDTO {
    private String url;
    private String descripcion;
    private Integer estaBanner;
    private Long empresaId;
} 
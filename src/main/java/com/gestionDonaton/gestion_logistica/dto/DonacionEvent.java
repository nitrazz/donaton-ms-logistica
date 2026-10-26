package com.gestionDonaton.gestion_logistica.dto;

import java.io.Serializable;

/**
 * Evento de donación recibido desde AWS RabbitMQ.
 * Implementado como Record nativo de Java 21.
 */
public record DonacionEvent(
        Long idDonacion,
        String donante,
        Double monto,
        String estado
) implements Serializable {}
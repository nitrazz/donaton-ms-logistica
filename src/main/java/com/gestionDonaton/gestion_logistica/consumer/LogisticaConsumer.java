package com.gestionDonaton.gestion_logistica.consumer;

import com.gestionDonaton.gestion_logistica.dto.DonacionEvent;
import com.gestionDonaton.gestion_logistica.service.EnvioService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LogisticaConsumer {

    private final EnvioService envioService;

    // Constructor explícito para inyección de Spring (Reemplaza a @RequiredArgsConstructor)
    @Autowired
    public LogisticaConsumer(EnvioService envioService) {
        this.envioService = envioService;
    }

    /**
     * Escucha eventos asíncronos publicados desde AWS RabbitMQ en la cola de logística (cola.documentos).
     */
    @RabbitListener(queues = "${app.rabbitmq.queues.logistica}")
    public void procesarOrdenLogistica(DonacionEvent evento) {
        System.out.println("📦 [MS-Logística / JDK 21] Evento recibido desde AWS RabbitMQ: " + evento);

        try {
            envioService.procesarEventoRabbitMQ(evento);
            System.out.println("✅ Orden de envío procesada y registrada en BD con éxito.");
        } catch (Exception e) {
            System.err.println("❌ Error al procesar el envío logístico: " + e.getMessage());
            throw e; // Lanzar la excepción permite reintentar o derivar a la DLQ
        }
    }
}
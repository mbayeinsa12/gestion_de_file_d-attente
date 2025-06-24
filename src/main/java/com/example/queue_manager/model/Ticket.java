// src/main/java/com/example/queuemanager/model/Ticket.java
package com.example.queue_manager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Génère automatiquement getters, setters, toString, equals, hashCode
@NoArgsConstructor // Génère un constructeur sans arguments
@AllArgsConstructor // Génère un constructeur avec tous les arguments
public class Ticket {

    private Long id;
    private String displayId; // Un identifiant lisible comme "A-101"

}
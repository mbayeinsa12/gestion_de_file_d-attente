// src/main/java/com/example/queuemanager/service/QueueService.java
package com.example.queue_manager.service;

import com.example.queue_manager.model.Ticket;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class QueueService {

    // Utilisation d'une liste thread-safe pour éviter les problèmes de concurrence.
    // Une simple ArrayList synchronisée manuellement fonctionnerait aussi.
    private final List<Ticket> ticketQueue = new CopyOnWriteArrayList<>();

    // Un compteur atomique pour générer des IDs uniques de manière sûre.
    private final AtomicLong idCounter = new AtomicLong();
    private final AtomicLong displayCounter = new AtomicLong(100);

    /**
     * Crée un nouveau ticket et l'ajoute à la fin de la file.
     * @return Le ticket qui a été créé.
     */
    public Ticket createNewTicket() {
        long newId = idCounter.incrementAndGet();
        String displayId = "T-" + displayCounter.incrementAndGet();
        Ticket newTicket = new Ticket(newId, displayId);
        ticketQueue.add(newTicket);
        return newTicket;
    }

    /**
     * Retourne la liste complète des tickets dans leur ordre actuel.
     * @return Une liste immuable de tickets.
     */
    public List<Ticket> getFullQueue() {
        return Collections.unmodifiableList(ticketQueue);
    }

    /**
     * Traite (supprime) le premier ticket de la file.
     * @return Le ticket qui a été traité, ou Optional.empty() si la file est vide.
     */
    public Optional<Ticket> processNextTicket() {
        if (ticketQueue.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(ticketQueue.remove(0));
    }

    /**
     * Trouve la position d'un ticket par son ID. L'index est 0-based.
     * @param id L'ID du ticket à trouver.
     * @return L'index du ticket, ou -1 s'il n'est pas trouvé.
     */
    private int findTicketIndexById(Long id) {
        for (int i = 0; i < ticketQueue.size(); i++) {
            if (ticketQueue.get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Fait avancer un ticket d'une position dans la file (le rapproche du début).
     * @param id L'ID du ticket à déplacer.
     */
    public void moveTicketUp(Long id) {
        int index = findTicketIndexById(id);
        // On ne peut pas monter si on est déjà le premier (index 0) ou si on n'est pas trouvé
        if (index > 0) {
            Collections.swap(ticketQueue, index, index - 1);
        }
    }

    /**
     * Fait reculer un ticket d'une position dans la file (l'éloigne du début).
     * @param id L'ID du ticket à déplacer.
     */
    public void moveTicketDown(Long id) {
        int index = findTicketIndexById(id);
        // On ne peut pas descendre si on est le dernier ou si on n'est pas trouvé
        if (index != -1 && index < ticketQueue.size() - 1) {
            Collections.swap(ticketQueue, index, index + 1);
        }
    }
}
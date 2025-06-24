// src/main/java/com/example/queuemanager/controller/QueueController.java
package com.example.queue_manager.controller;

import com.example.queue_manager.service.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/queue") // Toutes les URLs de ce contrôleur commenceront par /queue
public class QueueController {

    private final QueueService queueService;

    @Autowired
    public QueueController(QueueService queueService) {
        this.queueService = queueService;
    }

    @GetMapping
    public String viewQueue(Model model) {
        model.addAttribute("tickets", queueService.getFullQueue());
        return "queue-view"; // Nom du fichier HTML dans /resources/templates
    }

    @PostMapping("/add")
    public String addTicket() {
        queueService.createNewTicket();
        return "redirect:/queue"; // Redirige vers la page principale
    }

    @PostMapping("/process")
    public String processNext() {
        queueService.processNextTicket();
        return "redirect:/queue";
    }

    @PostMapping("/move-up/{id}")
    public String moveUp(@PathVariable Long id) {
        queueService.moveTicketUp(id);
        return "redirect:/queue";
    }

    @PostMapping("/move-down/{id}")
    public String moveDown(@PathVariable Long id) {
        queueService.moveTicketDown(id);
        return "redirect:/queue";
    }
//    @DeleteMapping("/move-down/{id}")
//    public String deleteTicket(@PathVariable Long id) {
//        queueService.moveTicketDown(id);
//        return "redirect:/queue";
//    }
}
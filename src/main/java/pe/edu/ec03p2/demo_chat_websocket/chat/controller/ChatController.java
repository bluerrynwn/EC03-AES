package pe.edu.ec03p2.demo_chat_websocket.chat.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import pe.edu.ec03p2.demo_chat_websocket.chat.model.Mensaje;

@Controller
public class ChatController {

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public Mensaje enviarmensajes(
            @Payload Mensaje mensaje){
        return mensaje;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public Mensaje agregarelUsuario(Mensaje mensaje,
                                    SimpMessageHeaderAccessor
                                            headerAccessor){
        headerAccessor.getSessionAttributes().put("username",
                mensaje.getEnvio());
        return mensaje;
    }
}
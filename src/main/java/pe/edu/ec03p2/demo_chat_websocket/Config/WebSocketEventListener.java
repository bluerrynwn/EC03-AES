package pe.edu.ec03p2.demo_chat_websocket.Config;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import pe.edu.ec03p2.demo_chat_websocket.Chatsito.Model.Mensaje;
import pe.edu.ec03p2.demo_chat_websocket.Chatsito.Model.TipoMensaje;

@Component
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messageSendingOperations;

    public WebSocketEventListener(SimpMessageSendingOperations messageSendingOperations) {
        this.messageSendingOperations = messageSendingOperations;
    }

    @EventListener
    public void socketDisconectListener(SessionDisconnectEvent event){
        StompHeaderAccessor headerAccessor =
                StompHeaderAccessor.wrap(event.getMessage());
        String usuario = (String)headerAccessor
                .getSessionAttributes().get("username");

        if(usuario != null){
            Mensaje mensaje = new Mensaje();
            mensaje.setTipo(TipoMensaje.DEJAR);
            mensaje.setEnvio(usuario);
            messageSendingOperations.convertAndSend(
                    "/topic/public", mensaje);
        }

    }
}

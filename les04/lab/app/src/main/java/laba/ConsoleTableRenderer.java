package laba;

import org.springframework.stereotype.Component;

@Component
public class ConsoleTableRenderer implements MessageRenderer {
    private MessageProvider messageProvider;

    @Override
    public void render() {
        if (messageProvider != null) {
            System.out.println(messageProvider.getMessage());
        }
    }

    @Override
    public void setMessageProvider(MessageProvider provider) {
        this.messageProvider = provider;
    }

    @Override
    public MessageProvider getMessageProvider() {
        return this.messageProvider;
    }
}
package laba;

public class OutMessageRenderer implements MessageRenderer {
    private MessageProvider messageProvider;

    @Override
    public void render() {
        if (messageProvider == null) {
            throw new RuntimeException("Ошибка: Провайдер не установлен!");
        }
        System.out.println(messageProvider.getMessage());
    }
}
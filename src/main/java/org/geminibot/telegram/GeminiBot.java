package org.geminibot.telegram;

import io.github.cdimascio.dotenv.Dotenv;
import org.geminibot.gemini.GeminiAPI;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class GeminiBot extends TelegramLongPollingBot {

    private static Dotenv dotenv = Dotenv.load();

    private final String BOT_USERNAME = "amr_gemini_bot";
    private static final String BOT_TOKEN = dotenv.get("TELEGRAM_BOT_TOKEN");

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {

        // Check if the update has a message and is a text message
        if (update.hasMessage() && update.getMessage().hasText()) {

            long chatId = update.getMessage().getChatId();
            String message = update.getMessage().getText();
            String username = update.getMessage().getFrom().getUserName();

            System.out.println("\nChatId: " + chatId);
            System.out.println("Username: " + username);
            System.out.println("Message: " + message);


            String botResponse = GeminiAPI.sendText(message,chatId);

            System.out.println("Gemini: " + botResponse+"\n");

            //To Amr: Divide Response less than 4096 char


            // Send response
            sendMessage(chatId, botResponse);
        }
    }

    private void sendMessage(long chatId, String text) {

        //To Amr: Fix styling
        text = convertMarkdownToHtml(text);

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        message.setParseMode("HTML");

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    public static String convertMarkdownToHtml(String text) {
        // Convert triple backtick code blocks ```code``` to <pre><code>code</code></pre>
        text = text.replaceAll("(?s)```(.*?)```", "<pre><code>$1</code></pre>");

        // Convert **bold** to <b>bold</b>
        text = text.replaceAll("\\*\\*(.*?)\\*\\*", "<b>$1</b>");

        // Convert *italic* or _italic_ to <i>italic</i>
        text = text.replaceAll("\\*(.*?)\\*", "<i>$1</i>")
                .replaceAll("_(.*?)_", "<i>$1</i>");

        // Convert inline `code` to <code>code</code>
        text = text.replaceAll("`(.*?)`", "<code>$1</code>");

        // Convert * bullet points to Telegram-compatible bullets
        text = text.replaceAll("(?m)^\\*\\s", "• ");

        return text;
    }
}

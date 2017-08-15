import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import javax.net.ssl.HttpsURLConnection;

public class Program {
    private final static String webAddress =
            "https://api.telegram.org/bot355829748:AAGr8YBz7RpKbs2Z6DCazQkyXYjWCQLOfrY/";
    private final static String getUpdatesRequest = "getUpdates";
    private final static String sendMessageRequest = "sendMessage";

    public static void main(String[] args) throws IOException, InterruptedException, TelegramApiRequestException {
//        int lastId = 0;
//
//        //noinspection InfiniteLoopStatement
//        while (true) {
//            // Get updates from the server
//            Gson gson = new Gson();
//
//            // Check, if status is ok
//            Response theResponse = gson.fromJson(getUpdate(lastId), new TypeToken<Response>(){}.getType());
//            if (!theResponse.ok)
//                continue;
//
//            // Translate each of the messages and respond to user
//            for (Update update : theResponse.result) {
//                sendMessage(update.message.from.id, Translator.translate(update.message.text));
//                lastId = update.update_id;
//            }
//
//            lastId++;
//            TimeUnit.SECONDS.sleep(10);
//        }

        ApiContextInitializer.init();
        TelegramBotsApi api = new TelegramBotsApi();
        api.registerBot(new Bot());
    }

    private static String getUpdate(int id) throws IOException {
        URLConnection conn = new URL(webAddress + getUpdatesRequest + "?offset=" + id).openConnection();

        InputStream is = conn.getInputStream();
        InputStreamReader reader = new InputStreamReader(is);
        char[] buffer = new char[256];
        int rc;
        StringBuilder sb = new StringBuilder();

        while ((rc = reader.read(buffer)) != -1)
            sb.append(buffer, 0, rc);
        reader.close();

        return sb.toString();
    }

    private static void sendMessage(int chatId, String message) throws IOException {
        URL url = new URL(webAddress + sendMessageRequest + "?chat_id=" + chatId + "&text=" + message);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        int responseCode = conn.getResponseCode();
    }
}

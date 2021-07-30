package okhttp3.guide;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class GetExample {

    OkHttpClient okHttpClient = new OkHttpClient();

    String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        try (Response response = okHttpClient.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public static void main(String[] args) throws IOException {
        GetExample example = new GetExample();
        String response = example.run("https://baike.baidu.com/item/%E6%96%87%E6%9C%AC/5443630");
        System.out.println(response);
    }
}

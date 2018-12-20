package hwa.helloworld.quiz_json;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    TextView tv_show, tv_show2, tv_show3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_show = (TextView)findViewById(R.id.tv_show);
        tv_show2 = (TextView)findViewById(R.id.tv_show2);

        String json = "{" + "\"user\": \"gildong\"," + "\"color\": [\"red\", \"green\", \"blue\"]"
                + "}";

        String json2 = "{" + "\"weather\": [\"id\": \"721\", \"main\": \"Haze\", \"description\": \"haze\", \"icon\": \"50n\"]" + "}"
                + "\"main\": [\"temp\": \"10.14\", \"pressure\": \"1020\", \"humidity\": \"37\", \"temp_min\": \"6\", \"temp_max\": \"13\"]"
                + "\"id\": \"18392\"," + "\"name\": \"Seoul\"," + "\"cod\": \"200\"" + "}";

        String json3 = "{\"weather\": [{\"id\": 721, \"main\": \"Haze\", \"description\": \"haze\", \"icon\": \"50n\"}]," +
                " \"main\": {\"temp\": 10.14, \"pressure\": 1020, \"humidity\": 37, \"temp_min\": 6, \"temp_max\": 13}, " +
                "\"id\": 18392," + "\"name\": \"Seoul\"," + "\"cod\": 200}";



        try {
            JSONObject root = new JSONObject(json3);

            /*String user_name = root.getString("user");
            JSONArray colors = root.getJSONArray("color");

            String first = colors.getString(0);
            String second = colors.getString(1);
            String third = colors.getString(2);*/


            JSONArray weather_s = root.getJSONArray("weather");
            JSONObject weather = weather_s.getJSONObject(0);

            int id_weather = weather.getInt("id");
            String main_weather = weather.getString("main");
            String haze_weather = weather.getString("description");
            String icon_weather = weather.getString("icon");

            JSONObject main = root.getJSONObject("main");
            double temp_main = main.getDouble("temp");
            int pressure_main = main.getInt("pressure");
            int humidity_main = main.getInt("humidity");
            int temp_min_main = main.getInt("temp_min");
            int temp_max_main = main.getInt("temp_max");

            int id = root.getInt("id");
            String name = root.getString("name");
            int cod = root.getInt("cod");


           /* for(int i=0; i<colors.length(); i++) {
                Log.d("show colors", colors.getString(i));
            }*/

            for(int i=0; i<weather_s.length(); i++) {
                Log.d("show weather", weather_s.getString(i));
            }

            //String result = "user: " + user_name + "\ncolor1: " + first + "\ncolor2: " + second + "\ncolor3: " + third;

            String result1 = "name: " + name + "\nweather1: " + weather;
            //tv_show.setText(result);
            tv_show2.setText(result1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

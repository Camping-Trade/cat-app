package com.catsruletheworld.cat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class LoginSuccess extends AppCompatActivity {
    final static String TAG = "CaT";

    LinearLayout home_body;
    BottomNavigationView bottomNavigationView;

    TextView campground;
    String Scampground;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_success);

        init(); //객체 정의
        SettingListener(); //세팅 리스너

        //첫 시작 탭
        getSupportFragmentManager().beginTransaction().replace(R.id.home_body, new Home()).commit();
        //bottomNavigationView.setSelectedItemId(R.id.home);

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        //GPS_PROVIDER: gps 사용, 야외, 정확도 높음. NETWORK_PROVIDER: 네트워크(wifi) 사용, 실내, 정확도 낮음.
        Location gpsLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Location wifiLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        double gps_lat = gpsLocation.getLatitude();
        double gps_lon = gpsLocation.getLongitude();

        double wifi_lat = wifiLocation.getLatitude();
        double wifi_lon = wifiLocation.getLongitude();

        Log.d(String.valueOf(gps_lat), "gps current latitude");
        Log.d(String.valueOf(gps_lon), "gps current longitude");

        Log.d(String.valueOf(wifi_lat), "wifi current latitude");
        Log.d(String.valueOf(wifi_lon), "wifi current longitude");

        campground = (TextView) findViewById(R.id.location);

        new Thread(new Runnable() {
            @Override
            public void run() {
                Scampground = getCampgroundLocation(gps_lon, gps_lat);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        campground.setText(Scampground);
                    }
                });
            }
        }).start();
    }

    private void init() {
        home_body = findViewById(R.id.home_body);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
    }

    private void SettingListener() {
        bottomNavigationView.setOnItemSelectedListener(new TabSelectedListener());
    }

    class TabSelectedListener implements BottomNavigationView.OnItemSelectedListener {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch(item.getItemId()){
                case R.id.tab_home: {
                    getSupportFragmentManager().beginTransaction().replace(R.id.home_body, new Home()).commit();
                    return true;
                }
                case R.id.tab_reservation: {
                    getSupportFragmentManager().beginTransaction().replace(R.id.home_body, new Reserve()).commit();
                    return true;
                }
                case R.id.tab_user: {
                    getSupportFragmentManager().beginTransaction().replace(R.id.home_body, new PersonalPage()).commit();
                    return true;
                }

              default:
                break;
            }

            return false;
        }
    }

    String getCampgroundLocation(double lon, double lat) {
        StringBuffer buffer = new StringBuffer();

        String serviceName = "CaT";
        String mobileOS = "AND"; //IOS, AND, ETC
        String serviceKey = "jxQzt0qN9lxYcJx1%2F8EEjIXVzQP2HzT9WcLLjF37gPuD9R78hRPJCmR8X8r7bVdtJl%2F4VKAhrnS%2BG4Ol6Dn7QQ%3D%3D";

        String queryURL = "http://api.visitkorea.or.kr/openapi/service/rest/GoCamping/locationBasedList?ServiceKey="+serviceKey+"MobileOS="+mobileOS+"&MobileApp="+serviceName+"&mapX="+lon+"&mapY="+lat+"&radius="+2000;

        try {
            URL url = new URL(queryURL);

            InputStream inputStream = url.openStream();

            XmlPullParserFactory parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = parserFactory.newPullParser();
            xmlPullParser.setInput(new InputStreamReader(inputStream, "UTF-8"));

            String tag;
            xmlPullParser.next();
            int eventType = xmlPullParser.getEventType();

            while(eventType != XmlPullParser.END_DOCUMENT) {
                switch(eventType){
                    case  XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        tag = xmlPullParser.getName();

                        if(tag.equals("item"));
                        else if (tag.equals("facltNm")) {
                            buffer.append("야영장명 : ");
                            xmlPullParser.next();
                            buffer.append(xmlPullParser.getText());
                            buffer.append("\n");
                        }
                    case XmlPullParser.TEXT:
                        break;
                    case XmlPullParser.END_TAG:
                        tag = xmlPullParser.getName();
                        if(tag.equals("item")) buffer.append("\n");
                        break;
                    default:
                        break;
                }
                eventType = xmlPullParser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return buffer.toString();
    }
}

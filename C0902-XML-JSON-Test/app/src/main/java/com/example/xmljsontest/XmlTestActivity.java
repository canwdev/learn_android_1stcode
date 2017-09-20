package com.example.xmljsontest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.xml.parsers.SAXParserFactory;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class XmlTestActivity extends AppCompatActivity {

    private final String TAG = "XML!";
    private TextView responseText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xml_test);

        responseText = (TextView) findViewById(R.id.textView_responseText);

        sendRequestWithOkHttp();
    }


    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(MainActivity.URL_XML)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseString = response.body().string();
                    parseXmlWithPull(responseString);
                    parseXmlWithSAX(responseString);
                    //showResponse(responseString);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    // Pull 解析方式
    private void parseXmlWithPull(String xmlString) {
        try {
            Log.d(TAG, "parseXmlWithPull: " + xmlString);
            // XmlPull解析工厂
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            // XmlPull 解析器
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader(xmlString));

            int eventType = xmlPullParser.getEventType();
            Log.d(TAG, "parseXmlWithPull: eventType=" + eventType);
            String id = "";
            String name = "";
            String version = "";
            String finString = "";

            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tempString = "";
                // 获取节点名称
                String nodeName = xmlPullParser.getName();
                switch (eventType) {
                    // 开始解析每个节点
                    case XmlPullParser.START_TAG: {
                        if ("id".equals(nodeName)) {
                            id = xmlPullParser.nextText();
                        } else if ("name".equals(nodeName)) {
                            name = xmlPullParser.nextText();
                        } else if ("version".equals(nodeName)) {
                            version = xmlPullParser.nextText();
                        }
                        break;
                    }
                    // 完成解析某个节点
                    case XmlPullParser.END_TAG: {
                        if ("app".equals(nodeName)) {
                            tempString += id + " " + name + " " + version + "\n";
                            Log.d(TAG, "parseXmlWithPull: tempString=" + id + " " + name + " " + version + "\n");
                        }
                        break;
                    }
                    default:
                        break;
                }
                finString += tempString;
                eventType = xmlPullParser.next();
            }
            showResponse(finString);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            Log.d(TAG, "parseXmlWithPull: " + e.toString());
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "parseXmlWithPull: " + e.toString());
        }

    }

    // SAX 解析方式
    private void parseXmlWithSAX(String xmlString) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            XMLReader xmlReader = factory.newSAXParser().getXMLReader();
            XmlContentHandler handler = new XmlContentHandler();
            xmlReader.setContentHandler(handler);
            // 开始解析
            xmlReader.parse(new InputSource(new StringReader(xmlString)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showResponse(final String string) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // UI 操作开线程
                responseText.setText(string);
            }
        });
    }
}

package com.example.xmlparser;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.StringReader;

public class MainActivity extends AppCompatActivity {

    TextView txt1, txt2;
    Button run;
    View.OnClickListener cl;
    String xml, res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        txt1 = (TextView) findViewById(R.id.text1);
        txt2 = (TextView) findViewById(R.id.text2);
        run = (Button) findViewById(R.id.run);
        txt1.setText(xml);


        cl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    XmlPullParserFactory fact;
                    XmlPullParser parser;
                    fact = XmlPullParserFactory.newInstance();
                    parser = fact.newPullParser();
                    InputStream is;
                    is = getResources().openRawResource(R.raw.info);
                    int n;
                    byte[] bt = new byte[is.available()];
                    n = is.read(bt);
                    if ( n <= 0)
                        return;
                    String s = new String(bt);
                    xml =s;
                    txt1.setText(xml);
                    parser.setInput(new StringReader(xml));                 //sd 카드에 읽은 때는 open file stream 으로 읽어낼 수 있다

                    int type;
                    boolean bname = false, bsex = false, btel = false, bage = false;
                    String tag;
                    res = "  이름  나이   성별        전화번호   \n";

                    type = parser.getEventType();
                    while ( type != XmlPullParser.END_DOCUMENT){
                        switch (type){
                            case XmlPullParser.START_DOCUMENT:
                                break;
                            case XmlPullParser.START_TAG:
                                tag = parser.getName();
                                if(tag.equals("name")){
                                    bname = true;
                                } else if ( tag.equals("sex")){
                                    bsex = true;
                                } else if ( tag.equals("tel")){
                                    btel = true;
                                } else if ( tag.equals("age")){
                                    bage = true;
                                }
                                break;

                            case XmlPullParser.TEXT :
                                if( bname) {
                                    res = res + parser.getText() + "  ";
                                    bname = false;
                                } else if(bsex){
                                    res = res + parser.getText() + "  ";
                                    bsex = false;
                                } else if (btel){
                                    res = res + parser.getText() + "\n";
                                    btel = false;
                                }else if (bage){
                                    res = res + parser.getText() + "  ";
                                    bage = false;
                                }
                                break;
                            case XmlPullParser.END_TAG:
                                break;
                        }
                        type = parser.next();
                    }
                    txt2.setText(res);


                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        };
        run.setOnClickListener(cl);

    }
}

package com.example.xmlparser;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

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

        xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?> \n";
        xml = xml + "<students>\n";
        xml = xml + "<student>\n" ;
        xml = xml + " <name> 홍길동 </name> <age>34</age> <sex>남성</sex>\n";
        xml = xml + "</student>\n";
        xml = xml + "<student>\n" ;
        xml = xml + " <name>  유관순 </name> <age>18</age> <sex>여성</sex>\n";
        xml = xml + "</student>\n";
        xml = xml + "<student>\n" ;
        xml = xml + " <name>  박병호 </name> <age>24</age> <sex>남성</sex>\n";
        xml = xml + "</student>\n";
        xml = xml + "<students>\n" ;

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
                    parser.setInput(new StringReader(xml));

                    int type;
                    boolean bname = false, bsex = false;
                    String tag;
                    res = "  이름     성별 \n";

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
                                }
                                break;

                            case XmlPullParser.TEXT :
                                if( bname) {
                                    res = res + parser.getText() + "  ";
                                    bname = false;
                                } else if(bsex){
                                    res = res + parser.getText() + "\n";
                                    bsex = false;
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

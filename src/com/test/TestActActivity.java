package com.test;//change this
import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.test.R; //change this

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestActActivity extends Activity implements TextToSpeech.OnInitListener 
{

	private static String say = null;
    private static final String TAG = "TextToSpeechDemo";
    private TextToSpeech mTts;
    private Button mAgainButton;
    private static String urlGet = null;

    public static String getWebpageText(String url) throws IOException {
    	  
        String sourceLine;
        String content = "";

        URL address = new URL(url);

        InputStreamReader pageInput = new InputStreamReader(address.openStream());
        BufferedReader source = new BufferedReader(pageInput);

        while ((sourceLine = source.readLine()) != null)
                content += sourceLine + "\t";

        Pattern style = Pattern.compile("<style.*?>.*?</style>");
        Matcher mstyle = style.matcher(content);
        while (mstyle.find()) content = mstyle.replaceAll("");

        Pattern script = Pattern.compile("<script.*?>.*?</script>");
        Matcher mscript = script.matcher(content);
        while (mscript.find()) content = mscript.replaceAll("");

        Pattern tag = Pattern.compile("<.*?>");
        Matcher mtag = tag.matcher(content);
        while (mtag.find()) content = mtag.replaceAll("");

        Pattern comment = Pattern.compile("<!--.*?-->");
        Matcher mcomment = comment.matcher(content);
        while (mcomment.find()) content = mcomment.replaceAll("");

        Pattern sChar = Pattern.compile("&.*?;");
        Matcher msChar = sChar.matcher(content);
        while (msChar.find()) content = msChar.replaceAll("");
        
        Pattern nLineChar = Pattern.compile("\t+");
        Matcher mnLine = nLineChar.matcher(content);
        while (mnLine.find()) content = mnLine.replaceAll("\n");
       
        return(content);
}
  
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // Initialize text-to-speech. This is an asynchronous operation.
        // The OnInitListener (second argument) is called after initialization completes.
        mTts = new TextToSpeech(this,this);
        mAgainButton = (Button) findViewById(R.id.aggbutton);
      
       
        mAgainButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
            	try{
            		 EditText mEdit   = (EditText)findViewById(R.id.urlField);
             	   urlGet = getWebpageText(mEdit.toString());
                }
                catch(Exception e)
                {
             	   e.printStackTrace();
                }
            	try{
            		mTts.speak(urlGet,TextToSpeech.QUEUE_FLUSH,null);
            		sayHello();
            	}
            	catch(Exception e)
            	{
            		Log.d(TAG, "Not working at onClick");
            	}
                
            }
        });
    }
    @Override
    public void onDestroy() 
    {
        // Don't forget to shutdown!
        if (mTts != null) {
        	
            mTts.stop();
            mTts.shutdown();
            
        }
        super.onDestroy();
    }
    // Implements TextToSpeech.OnInitListener.
    public void onInit(int status) 
    {
        // status can be either TextToSpeech.SUCCESS or TextToSpeech.ERROR.
        if (status == TextToSpeech.SUCCESS) 
        {
                       int result = mTts.setLanguage(Locale.US);
           
            if (result == TextToSpeech.LANG_MISSING_DATA ||
                result == TextToSpeech.LANG_NOT_SUPPORTED) 
            {
               // Lanuage data is missing or the language is not supported.
                Log.e(TAG, "Language is not available.");
            } 
            else 
            {
                // Check the documentation for other possible result codes.
                // For example, the language may be available for the locale,
                // but not for the specified country and variant.
                // The TTS engine has been successfully initialized.
                // Allow the user to press the button for the app to speak again.
                mAgainButton.setEnabled(true);
                // Greet the user.
                sayHello();
            }
        } 
        else
        {
            // Initialization failed.
            Log.e(TAG, "What you need, DDK");
        }
    }
   
    
    

    
    int i =0;
    private void sayHello() 
    {
        // Select a random hello.        
        Log.e(TAG, "going thru sayHello");
        
        mTts.speak(urlGet,TextToSpeech.QUEUE_FLUSH,null);
    }
}

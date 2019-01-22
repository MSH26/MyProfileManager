package stcorp.development.profilemanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class ProfileManagerActivity extends Activity {
	public Button onButton;
	public Button offButton;
	public TextView textView1;
	

    boolean flag = false;
    Intent i;
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public void onStart() {
        super.onStart();
        
        setContentView(R.layout.main);
    }
    
    @Override
    public void onResume() {
        super.onResume();

        onButton = (Button) this.findViewById(R.id.onButton);
        offButton = (Button) this.findViewById(R.id.offButton);
        textView1 = (TextView) this.findViewById(R.id.textView1);

        onButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                call_intent();
                textView1.setText("Service Started!");
//                Toast.makeText(getApplicationContext(),"Service started",Toast.LENGTH_SHORT).show();
            }
        });
        offButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                stop_intent();
                textView1.setText("Service Closed!");
//                Toast.makeText(getApplicationContext(),"Service closed",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        
        onButton = null;
        offButton = null;
    }

    @Override
    protected void onStop() {
        super.onStop();

        setVisible(false); 
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        setVisible(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    

    public void call_intent()
    {
        Intent i=new Intent(this,AudioService.class);
        startService(i);
        flag = true;
    }
    public void stop_intent()
    {
        i=new Intent(this,AudioService.class);
        stopService(i);
        flag = false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
    		if(flag){
    			stopService(i);
    		}
        	finish();
    		
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}

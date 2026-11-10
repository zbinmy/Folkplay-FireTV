package com.zbinmy.folkplaytv
import android.app.Activity
import android.os.Bundle
import android.content.Intent
import android.graphics.Color
import android.view.Gravity
import android.view.KeyEvent
import android.widget.LinearLayout
import android.widget.TextView
class MainActivity:Activity(){
 private var selected=0
 private val items=mutableListOf<TextView>()
 override fun onCreate(savedInstanceState:Bundle?){super.onCreate(savedInstanceState);window.decorView.systemUiVisibility=5894
 val root=LinearLayout(this).apply{orientation=LinearLayout.VERTICAL;setPadding(42,22,42,18);setBackgroundColor(Color.rgb(12,18,29))}
 root.addView(TextView(this).apply{text="FOLKPLAY ARCADE";textSize=32f;setTextColor(Color.rgb(104,232,190))})
 root.addView(TextView(this).apply{text="40 GAMES  •  D-PAD TO BROWSE  •  OK TO PLAY";textSize=16f;setTextColor(Color.LTGRAY);setPadding(0,6,0,12)})
 val list=LinearLayout(this).apply{orientation=LinearLayout.VERTICAL}
 ArcadeCatalog.games.forEachIndexed{index,g->val t=TextView(this).apply{text=(index+1).toString()+".  "+g.name+"   ·   "+g.genre;textSize=17f;gravity=Gravity.CENTER_VERTICAL;setTextColor(Color.WHITE);setPadding(14,0,8,0);isFocusable=true;isFocusableInTouchMode=true;setBackgroundColor(Color.rgb(27,40,54));setOnFocusChangeListener{v,f->v.setBackgroundColor(if(f)Color.rgb(45,91,84)else Color.rgb(27,40,54))};setOnClickListener{launch(index)}}
 val lp=LinearLayout.LayoutParams(-1,45);lp.bottomMargin=4;list.addView(t,lp);items.add(t)}
 root.addView(list);setContentView(root);items[0].post{items[0].requestFocus()}}
 private fun launch(i:Int){if(i==0)startActivity(Intent(this,GameActivity::class.java))else startActivity(Intent(this,ArcadeActivity::class.java).putExtra("game_index",i))}
 override fun onKeyDown(k:Int,e:KeyEvent?):Boolean{if(k==KeyEvent.KEYCODE_DPAD_DOWN||k==KeyEvent.KEYCODE_DPAD_UP){selected=(selected+if(k==KeyEvent.KEYCODE_DPAD_DOWN)1 else -1).coerceIn(0,items.lastIndex);items[selected].requestFocus();return true};if(k==KeyEvent.KEYCODE_DPAD_CENTER||k==KeyEvent.KEYCODE_ENTER){launch(selected);return true};return super.onKeyDown(k,e)}
}
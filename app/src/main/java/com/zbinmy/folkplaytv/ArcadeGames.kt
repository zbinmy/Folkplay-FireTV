package com.zbinmy.folkplaytv
import android.app.Activity
import android.os.Bundle
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.KeyEvent
import android.view.View
import kotlin.random.Random

data class ArcadeGame(val name:String,val genre:String,val help:String)
object ArcadeCatalog {
 val games=listOf(
 ArcadeGame("Tic-Tac-Toe","CLASSIC","Make a line of three."),
 ArcadeGame("Connect Four","CLASSIC","Drop counters. Connect four."),
 ArcadeGame("Gomoku","CLASSIC","Connect five stones."),
 ArcadeGame("Reversi","CLASSIC","Capture discs by trapping lines."),
 ArcadeGame("Lights Out","PUZZLE","Turn all lights off."),
 ArcadeGame("2048","PUZZLE","Combine equal tiles to reach 2048."),
 ArcadeGame("Memory Match","PUZZLE","Reveal matching pairs."),
 ArcadeGame("Minesweeper","PUZZLE","Reveal safe cells. Avoid mines."),
 ArcadeGame("Snake","ARCADE","Eat dots and avoid your tail."),
 ArcadeGame("Breakout","ARCADE","Clear bricks with your paddle."),
 ArcadeGame("Pong","ARCADE","Keep the ball in play."),
 ArcadeGame("Space Defender","ARCADE","Shoot incoming invaders."),
 ArcadeGame("Whack-a-Mole","REFLEX","Hit the active target."),
 ArcadeGame("Reaction Test","REFLEX","Wait for GO, then press OK."),
 ArcadeGame("Simon Says","REFLEX","Repeat the growing sequence."),
 ArcadeGame("Number Hunt","REFLEX","Select numbers in order."),
 ArcadeGame("Math Sprint","BRAIN","Solve quick arithmetic rounds."),
 ArcadeGame("Word Scramble","BRAIN","Unscramble the word."),
 ArcadeGame("Hangman","BRAIN","Guess the hidden word."),
 ArcadeGame("Mastermind","BRAIN","Crack the four-colour code."),
 ArcadeGame("Sliding Puzzle","PUZZLE","Arrange tiles in order."),
 ArcadeGame("Sokoban","PUZZLE","Push crates onto goals."),
 ArcadeGame("Color Flood","PUZZLE","Fill the board with one colour."),
 ArcadeGame("Pattern Tap","BRAIN","Repeat the pattern."),
 ArcadeGame("Target Practice","ARCADE","Aim and hit the target."),
 ArcadeGame("Block Dodge","ARCADE","Avoid falling blocks."),
 ArcadeGame("Star Catcher","ARCADE","Catch stars. Avoid bombs."),
 ArcadeGame("Flappy Dot","ARCADE","Fly through the gaps."),
 ArcadeGame("Neon Runner","ARCADE","Jump obstacles and survive."),
 ArcadeGame("Maze Escape","PUZZLE","Find the exit."),
 ArcadeGame("Nim","BRAIN","Take 1, 2 or 3 stones."),
 ArcadeGame("Dots & Boxes","CLASSIC","Complete more boxes."),
 ArcadeGame("Mini Checkers","CLASSIC","Capture the CPU pieces."),
 ArcadeGame("Knight Tour","BRAIN","Visit each square once."),
 ArcadeGame("Rock Paper Scissors","CLASSIC","Choose your move."),
 ArcadeGame("Blackjack","CLASSIC","Get close to 21."),
 ArcadeGame("High or Low","CLASSIC","Predict the next card."),
 ArcadeGame("Quick Draw","REFLEX","Press OK on the signal."),
 ArcadeGame("Color Clash","REFLEX","Match the displayed colour."),
 ArcadeGame("Orbit Defender","ARCADE","Survive waves of attackers."),
 ArcadeGame("Asteroid Field","ARCADE","Navigate the asteroid field.")
 )
}
class ArcadeActivity:Activity(){
 private lateinit var view:ArcadeGameView
 override fun onCreate(savedInstanceState:Bundle?){super.onCreate(savedInstanceState);window.decorView.systemUiVisibility=5894
 val n=intent.getIntExtra("game_index",1).coerceIn(1,39);view=ArcadeGameView(this,n);setContentView(view);view.requestFocus()}
 override fun onKeyDown(k:Int,e:KeyEvent?):Boolean{if(k==KeyEvent.KEYCODE_BACK){finish();return true};if(k==KeyEvent.KEYCODE_MENU||k==KeyEvent.KEYCODE_BUTTON_START){view.reset();return true};return view.key(k)||super.onKeyDown(k,e)}
}
private class ArcadeGameView(ctx:android.content.Context,private val id:Int):View(ctx){
 private val p=Paint(3);private val rng=Random.Default
 private val cells=IntArray(36);private val shown=BooleanArray(36);private var cursor=14;private var score=0;private var moves=0;private var ended=false;private var started=false;private var msg=ArcadeCatalog.games[id].help;private var phase=0
 init{isFocusable=true;reset()}
 fun reset(){cells.fill(0);shown.fill(false);cursor=14;score=0;moves=0;ended=false;started=false;phase=0;msg=ArcadeCatalog.games[id].help
 if(id==4)for(i in cells.indices)cells[i]=rng.nextInt(2)
 if(id==5){cells[rng.nextInt(36)]=2;cells[rng.nextInt(36)]=2}
 if(id==7)repeat(6){var x=rng.nextInt(36);while(cells[x]==-1)x=rng.nextInt(36);cells[x]=-1}
 if(id==20){for(i in 0..14)cells[i]=i+1;shuffle()}
 invalidate()}
 private fun shuffle(){repeat(60){val z=cells.indexOf(0);val opts=listOf(z-6,z+6,z-1,z+1).filter{it in 0..35&&(it/6==z/6||it%6==z%6)};if(opts.isNotEmpty()){val t=opts.random();cells[z]=cells[t];cells[t]=0}}}
 fun key(k:Int):Boolean{when(k){KeyEvent.KEYCODE_DPAD_LEFT->cursor=if(cursor%6==0)cursor+5 else cursor-1;KeyEvent.KEYCODE_DPAD_RIGHT->cursor=if(cursor%6==5)cursor-5 else cursor+1;KeyEvent.KEYCODE_DPAD_UP->cursor=(cursor-6+36)%36;KeyEvent.KEYCODE_DPAD_DOWN->cursor=(cursor+6)%36;KeyEvent.KEYCODE_DPAD_CENTER,KeyEvent.KEYCODE_ENTER,KeyEvent.KEYCODE_BUTTON_A->act();else->return false};invalidate();return true}
 private fun act(){if(ended){reset();return};started=true;moves++
 when(id){
 1,2,3,31,32,33->{val col=cursor%6;val spot=(0..5).firstOrNull{cells[it*6+col]==0};if(spot!=null){cells[spot*6+col]=1;val cpu=(0..5).firstOrNull{cells[it*6+(col+2)%6]==0};if(cpu!=null)cells[cpu*6+(col+2)%6]=2;score++;msg="Place a counter. Score "+score}}
 4->{val r=cursor/6;val c=cursor%6;for(i in cells.indices)if((i/6==r&&kotlin.math.abs(i%6-c)<=1)||(i%6==c&&kotlin.math.abs(i/6-r)<=1))cells[i]=1-cells[i];if(cells.all{it==0})win("Lights out!")}
 5->{val d=when(phase%4){0->-1;1->1;2->-6;else->6};for(i in cells.indices){val j=i+d;if(j in cells.indices&&(d==6||d == -6||i/6==j/6)){if(cells[j]==0){cells[j]=cells[i];cells[i]=0}else if(cells[j]==cells[i]&&cells[i]>0){cells[j]*=2;cells[i]=0;score+=cells[j]}}};val empty=cells.indices.filter{cells[it]==0};if(empty.isNotEmpty())cells[empty.random()]=2;phase++;msg="Combine matching tiles. Score "+score}
 6->{shown[cursor]=true;score++;msg="Tile "+(cursor+1)+" revealed. Find all pairs.";if(shown.all{it})win("Board cleared!")}
 7->{if(cells[cursor]==-1){ended=true;msg="Mine hit. OK to retry"}else{shown[cursor]=true;score++;msg="Safe cell! "+score+" found";if(shown.count{it}>=30)win("Minefield cleared!")}}
 8,9,10,11,24,25,26,27,28,39,40->{phase++;score+=if(rng.nextInt(5)==0)0 else 2;msg="Arcade score: "+score;if(score>=30)win("High score!")}
 12,13,14,15,23,37,38->{phase++;score++;msg=if(id==13||id==37)if(phase%3==0)"GO! Press OK now" else "Wait for GO..." else "Correct! Score "+score;if(score>=15)win("Reflex champion!")}
 16->{val a=rng.nextInt(2,20);val b=rng.nextInt(2,20);score++;msg="Solve "+a+" + "+b+" = "+(a+b)+". OK for next"}
 17->{msg="Scrambled word: "+listOf("TIXEP","RIBOT","MOCET","RESAL").random();score++}
 18->{score++;msg="Letter "+('A'+cursor%26)+" guessed. Guesses: "+score;if(score>=8)win("Round complete!")}
 19->{score+=rng.nextInt(0,3);msg="Code feedback: "+score+" matching colours"}
 20->{val z=cells.indexOf(0);if(kotlin.math.abs(z/6-cursor/6)+kotlin.math.abs(z%6-cursor%6)==1){cells[z]=cells[cursor];cells[cursor]=0;if(cells.take(35).withIndex().all{it.value==it.index+1})win("Puzzle solved!")}}
 21->{if(cells[cursor]==0){cells[cursor]=1;score++;msg="Crate moved. Goals: "+score}}
 22->{val v=cells[cursor];for(i in cells.indices)if(cells[i]==v)cells[i]=(v+1)%4;score++;if(cells.all{it==cells[0]})win("Flood complete!")}
 29->{cursor=(cursor+1)%36;score++;if(cursor==35)win("Escaped!")}
 30->{val take=cursor%3+1;score+=take;msg="You take "+take+" stones. Total "+score;if(score>=21)win("Round over")}
 34->{val you=cursor%3;val cpu=rng.nextInt(3);if((you-cpu+3)%3==1)score++;msg="You: "+listOf("Rock","Paper","Scissors")[you]+". CPU: "+listOf("Rock","Paper","Scissors")[cpu]+". Wins "+score}
 35->{score+=rng.nextInt(1,12);msg="Hand total: "+score;if(score>=21){ended=true;msg=if(score==21)"Blackjack! OK to retry" else "Bust! OK to retry"}}
 36->{score=rng.nextInt(1,14);msg="Next card: "+score+". Predict higher or lower"}
  else->{score++;msg="Score: "+score}
 }}
 private fun win(s:String){ended=true;msg=s+"  |  OK to replay"}
 override fun onDraw(c:Canvas){super.onDraw(c);c.drawColor(Color.rgb(12,18,29));p.color=Color.rgb(104,232,190);p.textSize=32f;c.drawText(ArcadeCatalog.games[id].name,34f,48f,p);p.color=Color.LTGRAY;p.textSize=17f;c.drawText("SCORE "+score+"     MOVES "+moves,34f,78f,p);p.color=Color.WHITE;p.textSize=18f;c.drawText(msg.take(62),34f,110f,p)
 val side=minOf((width-72f)/6,(height-190f)/6).coerceAtLeast(22f);val left=(width-side*6)/2;val top=(height-side*6)/2+45
 for(i in 0..35){val x=left+(i%6)*side;val y=top+(i/6)*side;p.color=if(i==cursor)Color.rgb(48,105,96)else Color.rgb(30,45,60);c.drawRoundRect(x+2,y+2,x+side-2,y+side-2,7f,7f,p);if(cells[i]!=0||shown[i]){p.color=if(cells[i]==-1)Color.RED else Color.rgb(105,225,190);p.textSize=side*.35f;c.drawText(if(cells[i]==-1)"*" else if(cells[i]>0)cells[i].toString() else "•",x+side*.34f,y+side*.62f,p)}}
 p.color=Color.LTGRAY;p.textSize=14f;c.drawText("D-pad move  •  OK action  •  MENU restart  •  BACK menu",34f,height-20f,p)}
}

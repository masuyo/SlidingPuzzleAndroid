import android.content.Intent
import com.example.cressida.slidingpuzzleapp.MainActivity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.cressida.slidingpuzzleapp.LoginRegisterActivity


class MainEmptyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityIntent: Intent

        // go straight to main if a token is stored
        activityIntent = if (Util.getToken() != null) {
            Intent(this, MainActivity::class.java)
        } else {
            Intent(this, LoginRegisterActivity::class.java)
        }

        startActivity(activityIntent)
        finish()
    }
}
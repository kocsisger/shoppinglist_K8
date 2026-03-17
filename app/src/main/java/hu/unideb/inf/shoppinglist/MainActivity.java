package hu.unideb.inf.shoppinglist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    public static final String TEXTVIEW_CONTENTS = "TEXTVIEW_CONTENTS";
    TextView itemsTextView;

    ActivityResultLauncher activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), activityResult -> {
        if (activityResult.getResultCode() != RESULT_OK) return;

        Log.d("ITEMS_TEST", "I have returned");
        Log.d("ITEMS_TEST", "Item: " + activityResult.getData().getStringExtra(ItemsActivity.ITEM));
        if (itemsTextView.getText().toString().equals(getString(R.string.empty_list)))
            itemsTextView.setText(activityResult.getData().getStringExtra(ItemsActivity.ITEM) + "\n");
        else
            itemsTextView.append(activityResult.getData().getStringExtra(ItemsActivity.ITEM) + "\n");
    });

    SharedPreferences sharedPreferences;
    String sharedPrefFileName = "shrdprf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sharedPreferences = getSharedPreferences(sharedPrefFileName, MODE_PRIVATE);

        itemsTextView = findViewById(R.id.itemsTextView);
        if (savedInstanceState != null)
            itemsTextView.setText(savedInstanceState.getString(TEXTVIEW_CONTENTS));
        else
            itemsTextView.setText(
                    sharedPreferences.getString(TEXTVIEW_CONTENTS, getString(R.string.empty_list))
            );
    }

    @Override
    protected void onStop() {
        super.onStop();
        sharedPreferences.edit().putString(TEXTVIEW_CONTENTS, itemsTextView.getText().toString()).apply();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(TEXTVIEW_CONTENTS, itemsTextView.getText().toString());
    }

    public void handleAddButtonPressed(View view) {
        Intent intent = new Intent(this, ItemsActivity.class);
        //startActivity(intent);
        activityResultLauncher.launch(intent);
    }

    public void handleClearButton(View view) {
        itemsTextView.setText("");
    }
}

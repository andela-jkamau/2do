package info.jimmykamau.todo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import info.jimmykamau.todo.adapters.TodoListAdapter;
import info.jimmykamau.todo.models.TodoItem;

public class MainActivity extends AppCompatActivity {

    private ListView mTodoItemsListView;
    private ProgressBar mTodoListProgress;
    private TextView mTodoListProgressText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTodoListProgressText = (TextView) findViewById(R.id.completion_progress_text);
        mTodoListProgress = (ProgressBar) findViewById(R.id.completion_progress_bar);
        mTodoListProgress.setMax(100);

        TodoListAdapter todoListAdapter = new TodoListAdapter(this);
        mTodoItemsListView = (ListView) findViewById(R.id.todo_items_list);
        mTodoItemsListView.setAdapter(todoListAdapter);

        updateProgress();
    }

    public void addTodoItem(View view) {
        EditText newItemInput = (EditText) findViewById(R.id.add_item_input);
        String itemTitle = newItemInput.getText().toString();
        TodoItem newItem = new TodoItem(itemTitle, "New item description", false);
        newItem.save();
        ((BaseAdapter) mTodoItemsListView.getAdapter()).notifyDataSetChanged();
        Toast.makeText(this, getString(R.string.todo_create_success), Toast.LENGTH_SHORT).show();
        newItemInput.setText("");
        updateProgress();
    }

    public void updateProgress() {
        List<TodoItem> todoItemsList = TodoItem.listAll(TodoItem.class);
        int completedItems = 0;
        for (TodoItem item : todoItemsList) {
            if(item.checkTodoComplete()) {
                completedItems++;
            }
        }
        int numberOfItems = todoItemsList.size();
        int currentProgress = 0;
        if (numberOfItems != 0) {
            currentProgress = (completedItems*100)/numberOfItems;
        }
        mTodoListProgress.setProgress(currentProgress);
        mTodoListProgressText.setText(getApplicationContext().getString(R.string.progress_text, currentProgress));
    }
}

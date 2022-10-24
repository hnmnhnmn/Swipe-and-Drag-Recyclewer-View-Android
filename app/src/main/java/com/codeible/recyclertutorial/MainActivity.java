package com.codeible.recyclertutorial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private final String[] TUTORIALS = {
            "Codeible.com",
            "Android Material UI",
            "Android Animations",
            "Android Views",
            "Android Navigation",
            "Android Notifications",
            "Android Services",
            "Google Play Billing"
    };
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private ArrayList<String> recyclerViewItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupRecyclerView();

        // TUTORIAL CODE STARTS HERE
        ItemTouchHelper touchHelper = new ItemTouchHelper(
                new ItemTouchHelper.Callback() {
                    @Override
                    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                        return makeMovementFlags(ItemTouchHelper.UP | ItemTouchHelper.DOWN,ItemTouchHelper.END | ItemTouchHelper.START);
                    }

                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        int draggedItemIndex = viewHolder.getAdapterPosition();
                        int targetIndex = target.getAdapterPosition();
                        Collections.swap(recyclerViewItems,draggedItemIndex,targetIndex);
                        adapter.notifyItemMoved(draggedItemIndex,targetIndex);
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        switch (direction) {
                            case ItemTouchHelper.END:
                            case ItemTouchHelper.START:
                                recyclerViewItems.remove(viewHolder.getAdapterPosition());
                                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                                break;
                        }
                    }
                }
        );
        touchHelper.attachToRecyclerView(recyclerView);

    }

    private void setupRecyclerView() {
        recyclerViewItems.addAll(Arrays.asList(TUTORIALS));
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter = new RecyclerViewAdapter());
    }

    private class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.bind(recyclerViewItems.get(position));
        }

        @Override
        public int getItemCount() {
            return recyclerViewItems.size();
        }

        private class ViewHolder extends RecyclerView.ViewHolder {
            private TextView title;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.item_title);
            }

            public void bind(String arrayItem) {
                title.setText(arrayItem);
            }
        }
    }
}
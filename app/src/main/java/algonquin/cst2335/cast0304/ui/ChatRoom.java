 package algonquin.cst2335.cast0304.ui;

 import android.os.Bundle;
 import android.view.View;
 import android.view.ViewGroup;
 import android.widget.TextView;

 import androidx.annotation.NonNull;
 import androidx.appcompat.app.AppCompatActivity;
 import androidx.lifecycle.ViewModelProvider;
 import androidx.recyclerview.widget.LinearLayoutManager;
 import androidx.recyclerview.widget.RecyclerView;

 import java.text.SimpleDateFormat;
 import java.util.ArrayList;
 import java.util.Date;

 import algonquin.cst2335.cast0304.R;
 import algonquin.cst2335.cast0304.data.ChatRoomViewModel;
 import algonquin.cst2335.cast0304.databinding.ActivityChatRoomBinding;
 import algonquin.cst2335.cast0304.databinding.ReceiveMessageBinding;
 import algonquin.cst2335.cast0304.databinding.SentMessageBinding;

 public class ChatRoom extends AppCompatActivity {

    ActivityChatRoomBinding binding;
    ChatRoomViewModel chatModel;
    ArrayList<ChatMessage> chatMessages;// new ArrayList<>();
    private RecyclerView.Adapter<MyRowHolder> myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);
        chatMessages = chatModel.chatMessages.getValue();
        if (chatMessages == null) {
            chatModel.chatMessages.postValue( chatMessages = new ArrayList<>());
        }

        binding.recycleView.setAdapter( myAdapter = new RecyclerView.Adapter<MyRowHolder>() {


            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                // inflates the row layout
                // int ViewType is what layout should be loaded
                if (viewType == 0){
                    SentMessageBinding messageBinding = SentMessageBinding.inflate(getLayoutInflater(), parent, false);

                    // initialize row variables
                    return new MyRowHolder( messageBinding.getRoot() );
                } else {
                    ReceiveMessageBinding messageBinding = ReceiveMessageBinding.inflate(getLayoutInflater(), parent, false);

                    // initialize row variables
                    return new MyRowHolder( messageBinding.getRoot() );
                }
            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                ChatMessage chatmessage = chatMessages.get(position);

                // override the text in the rows
                holder.messageText.setText(chatmessage.getMessage());
                holder.timeText.setText(chatmessage.getTimeSent());
            }

            @Override
            public int getItemCount() {

                return chatMessages.size();
            }

            public int getItemViewType(int position) {
                boolean isSentButton = chatMessages.get(position).isSentButton();
                return isSentButton ? 0 : 1;
            }
        });

        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));

        // send button
        binding.sendButton.setOnClickListener(click -> {
            // create chat message object
            ChatMessage chatmessage = new ChatMessage();
            String text = binding.textInput.getText().toString();
            String time = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a").format(new Date());

            chatmessage.setMessage(text);
            chatmessage.setTimeSent(time);
            chatmessage.setSentButton(true);
            chatMessages.add(chatmessage);

            // notify the adapter
            myAdapter.notifyItemInserted( chatMessages.size()-1 ); // by rows

            // clears the previous text
            binding.textInput.setText("");
        });

        // receive button
        binding.receiveButton.setOnClickListener(click -> {
            // create chat message object
            ChatMessage chatmessage = new ChatMessage();
            String text = binding.textInput.getText().toString();
            String time = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a").format(new Date());

            chatmessage.setMessage(text);
            chatmessage.setTimeSent(time);
            chatmessage.setSentButton(false);
            chatMessages.add(chatmessage);

            // notify the adapter
            myAdapter.notifyItemInserted( chatMessages.size()-1 ); // by rows

            // clears the previous text
            binding.textInput.setText("");
        });
    }

    // class represents one row and it holds the variables
    class MyRowHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        TextView timeText;

        // initializes variables
        public MyRowHolder(@NonNull View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.messageText);
            timeText = itemView.findViewById(R.id.timeText);
        }
    }
}
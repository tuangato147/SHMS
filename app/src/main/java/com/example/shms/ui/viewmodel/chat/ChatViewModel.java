package com.example.shms.ui.viewmodel.chat;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.shms.data.local.entities.Message;
import com.example.shms.data.repository.ChatRepository;
import com.example.shms.utils.BaseViewModel;
import java.util.List;

public class ChatViewModel extends BaseViewModel {
    protected ChatRepository chatRepository;
    protected MutableLiveData<String> messageInput = new MutableLiveData<>();
    protected MutableLiveData<Boolean> canSendMessage = new MutableLiveData<>(false);
    protected LiveData<List<Message>> messages;

    public ChatViewModel(Application application) {
        super(application);
        chatRepository = new ChatRepository(application);
        messageInput.observeForever(input ->
                canSendMessage.setValue(input != null && !input.trim().isEmpty())
        );
    }

    public void sendMessage(int senderId, int receiverId) {
        String content = messageInput.getValue();
        if (content == null || content.trim().isEmpty()) {
            return;
        }

        Message message = new Message();
        message.setSenderId(senderId);
        message.setReceiverId(receiverId);
        message.setContent(content);
        message.setTimestamp(System.currentTimeMillis());

        setLoading(true);
        chatRepository.sendMessage(message, success -> {
            setLoading(false);
            if (success) {
                messageInput.setValue("");
            } else {
                showError("Không thể gửi tin nhắn");
            }
        });
    }

    public LiveData<List<Message>> getMessages() {
        return messages;
    }

    public MutableLiveData<String> getMessageInput() {
        return messageInput;
    }

    public MutableLiveData<Boolean> getCanSendMessage() {
        return canSendMessage;
    }
}
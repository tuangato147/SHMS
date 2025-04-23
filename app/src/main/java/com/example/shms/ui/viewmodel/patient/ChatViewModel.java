package com.example.shms.ui.viewmodel.patient;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.shms.data.local.entities.ChatMessage;
import com.example.shms.data.local.entities.ChatRoom;
import com.example.shms.data.repository.ChatRepository;
import com.example.shms.utils.BaseViewModel;
import java.util.List;

public class ChatViewModel extends BaseViewModel {
    private ChatRepository repository;
    private LiveData<List<ChatRoom>> chatRooms;
    private MutableLiveData<ChatRoom> selectedRoom = new MutableLiveData<>();
    private LiveData<List<ChatMessage>> messages;
    private MutableLiveData<String> messageText = new MutableLiveData<>();

    public ChatViewModel(Application application) {
        super(application);
        repository = new ChatRepository(application);
        loadChatRooms();
    }

    private void loadChatRooms() {
        int patientId = getCurrentUserId();
        chatRooms = repository.getPatientChatRooms(patientId);
    }

    public void selectChatRoom(ChatRoom room) {
        selectedRoom.setValue(room);
        loadMessages(room.getId());
    }

    private void loadMessages(int roomId) {
        messages = repository.getChatMessages(roomId);
    }

    public void sendMessage() {
        String text = messageText.getValue();
        if (text == null || text.trim().isEmpty()) {
            return;
        }

        ChatRoom room = selectedRoom.getValue();
        if (room == null) {
            showError("Vui lòng chọn phòng chat");
            return;
        }

        setLoading(true);
        ChatMessage message = new ChatMessage();
        message.setRoomId(room.getId());
        message.setSenderId(getCurrentUserId());
        message.setContent(text);
        message.setTimestamp(System.currentTimeMillis());

        repository.sendMessage(message, (success, error) -> {
            setLoading(false);
            if (success) {
                messageText.setValue("");
            } else {
                showError(error);
            }
        });
    }

    public void createNewChatRoom(int doctorId) {
        setLoading(true);
        repository.createChatRoom(getCurrentUserId(), doctorId, (room, error) -> {
            setLoading(false);
            if (room != null) {
                selectChatRoom(room);
            } else {
                showError(error);
            }
        });
    }

    // Getters for data binding
    public LiveData<List<ChatRoom>> getChatRooms() {
        return chatRooms;
    }

    public MutableLiveData<ChatRoom> getSelectedRoom() {
        return selectedRoom;
    }

    public LiveData<List<ChatMessage>> getMessages() {
        return messages;
    }

    public MutableLiveData<String> getMessageText() {
        return messageText;
    }
}
package ru.vokazak;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import ru.vokazak.jda.JdaFactory;
import ru.vokazak.jda.MsgHandler;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Component
@FxmlView("messagePanel.fxml")
public class MessagePanelController implements Initializable {

    private final JDA jda;

    private TextChannel currentChannel;
    private User currentUser;

    public MessagePanelController(JdaFactory jdaFactory) {
        this.jda = jdaFactory.getJda();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        channelChoiceBox.setItems(getChannelChoiceBoxList());
        channelChoiceBox.setValue(channelChoiceBox.getItems().get(0));
        currentChannel = jda.getTextChannels().get(0);

        userChoiceBox.setItems(getUserChoiceBoxList());
        userChoiceBox.setValue(userChoiceBox.getItems().get(0));
        currentUser = jda.getUsers().get(0);
    }

    @FXML
    private ChoiceBox<String> channelChoiceBox;

    @FXML
    private ChoiceBox<String> userChoiceBox;

    @FXML
    private TextField textField;

    @FXML
    private void onSendButtonClicked() {
        currentChannel = jda.getTextChannelsByName(channelChoiceBox.getValue(), true).get(0);
        currentChannel.sendMessage(textField.getText()).queue();
        textField.clear();
    }

    @FXML
    private void onInviteAllButtonClicked() {
        MsgHandler.sendInvitation(currentChannel);
    }

    @FXML
    private void onInviteButtonClicked() {
        currentChannel = jda.getTextChannelsByName(channelChoiceBox.getValue(), true).get(0);
        currentUser = jda.getUsersByName(userChoiceBox.getValue(), true).get(0);
        currentChannel.sendMessage("<@" + currentUser.getId() + ">").queue();
    }

    private ObservableList<String> getChannelChoiceBoxList() {
        List<String> textChannelList = new ArrayList<>();
        for (TextChannel c: jda.getTextChannels()) {
            String channelName = c.toString().substring(3).replaceAll("\\([0-9]*\\)", "");
            textChannelList.add(channelName);
        }

        return FXCollections.observableList(textChannelList);
    }

    private ObservableList<String> getUserChoiceBoxList() {
        List<String> userList = new ArrayList<>();
        for (User u: jda.getUsers()) {
            userList.add(u.getName());
        }

        return FXCollections.observableList(userList);
    }

}
